package com.pricingengine.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.pricingengine.model.RuleConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VehicleValidator implements Validator {
    @Override
    public List<ValidationError> validate(JsonNode request, JsonNode quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        RuleConfig.VehicleRules rules = config.vehicleRules;

        JsonNode vehicle = readVehicleNode(request);

        String bodyCategory = vehicle.path("BodyCategoryCode").asText(vehicle.path("bodyCategoryCode").asText(""));
        if (rules.allowedBodyCategoryCodes != null && !rules.allowedBodyCategoryCodes.contains(bodyCategory)) {
            errors.add(new ValidationError("VEHICLE_BODY_CATEGORY", quotationRef(quotation),
                    String.valueOf(rules.allowedBodyCategoryCodes), bodyCategory));
        }

        int manufactureYear = vehicle.path("ManufactureYear").asInt(vehicle.path("manufactureYear").asInt(-1));
        if (rules.minManufactureYear != null && manufactureYear < rules.minManufactureYear) {
            errors.add(new ValidationError("VEHICLE_MIN_YEAR", quotationRef(quotation),
                    ">= " + rules.minManufactureYear, String.valueOf(manufactureYear)));
        }
        if (rules.maxManufactureYear != null && manufactureYear > rules.maxManufactureYear) {
            errors.add(new ValidationError("VEHICLE_MAX_YEAR", quotationRef(quotation),
                    "<= " + rules.maxManufactureYear, String.valueOf(manufactureYear)));
        }

        BigDecimal estimatedValue = vehicle.path("EstimatedValue").decimalValue();
        if (vehicle.path("EstimatedValue").isMissingNode()) {
            estimatedValue = vehicle.path("estimatedValue").decimalValue();
        }
        if (rules.minEstimatedValue != null && estimatedValue.compareTo(rules.minEstimatedValue) < 0) {
            errors.add(new ValidationError("VEHICLE_MIN_VALUE", quotationRef(quotation),
                    ">= " + rules.minEstimatedValue, estimatedValue.toPlainString()));
        }
        if (rules.maxEstimatedValue != null && estimatedValue.compareTo(rules.maxEstimatedValue) > 0) {
            errors.add(new ValidationError("VEHICLE_MAX_VALUE", quotationRef(quotation),
                    "<= " + rules.maxEstimatedValue, estimatedValue.toPlainString()));
        }

        int insuranceType = quotation.path("vehicleInsuranceType").asInt(-1);
        if (!rules.allowedVehicleInsuranceTypes.contains(insuranceType)) {
            errors.add(new ValidationError("VEHICLE_INSURANCE_TYPE", quotationRef(quotation),
                    String.valueOf(rules.allowedVehicleInsuranceTypes), String.valueOf(insuranceType)));
        }

        return errors;
    }

    private JsonNode readVehicleNode(JsonNode request) {
        JsonNode car = request.path("Car");
        if (!car.isMissingNode() && !car.isNull()) {
            return car;
        }
        return request.path("vehicle");
    }

    private String quotationRef(JsonNode quotation) {
        return quotation.path("quotationNo").asText("unknown") + "/" + quotation.path("planCode").asText("unknown");
    }
}
