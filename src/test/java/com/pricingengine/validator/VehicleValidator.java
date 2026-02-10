package com.pricingengine.validator;

import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.api.request.PricingEngineRequest;
import com.pricingengine.model.api.request.Vehicle;
import com.pricingengine.model.api.response.QuotationResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VehicleValidator implements Validator {
    @Override
    public List<ValidationError> validate(PricingEngineRequest request, QuotationResponse quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        RuleConfig.VehicleRules rules = config.vehicleRules;

        Vehicle vehicle = request.getVehicle();
        if (vehicle == null) {
            errors.add(new ValidationError("VEHICLE_PRESENT", quotationRef(quotation), "vehicle present", "missing"));
            return errors;
        }

        String bodyCategory = vehicle.getBodyCategoryCode();
        if (rules.allowedBodyCategoryCodes != null && !rules.allowedBodyCategoryCodes.contains(bodyCategory)) {
            errors.add(new ValidationError("VEHICLE_BODY_CATEGORY", quotationRef(quotation),
                    String.valueOf(rules.allowedBodyCategoryCodes), bodyCategory));
        }

        int manufactureYear = vehicle.getManufactureYear() != null ? vehicle.getManufactureYear() : -1;
        if (rules.minManufactureYear != null && manufactureYear < rules.minManufactureYear) {
            errors.add(new ValidationError("VEHICLE_MIN_YEAR", quotationRef(quotation),
                    ">= " + rules.minManufactureYear, String.valueOf(manufactureYear)));
        }
        if (rules.maxManufactureYear != null && manufactureYear > rules.maxManufactureYear) {
            errors.add(new ValidationError("VEHICLE_MAX_YEAR", quotationRef(quotation),
                    "<= " + rules.maxManufactureYear, String.valueOf(manufactureYear)));
        }

        BigDecimal estimatedValue = vehicle.getEstimatedValue() == null ? BigDecimal.ZERO : vehicle.getEstimatedValue();
        if (rules.minEstimatedValue != null && estimatedValue.compareTo(rules.minEstimatedValue) < 0) {
            errors.add(new ValidationError("VEHICLE_MIN_VALUE", quotationRef(quotation),
                    ">= " + rules.minEstimatedValue, estimatedValue.toPlainString()));
        }
        if (rules.maxEstimatedValue != null && estimatedValue.compareTo(rules.maxEstimatedValue) > 0) {
            errors.add(new ValidationError("VEHICLE_MAX_VALUE", quotationRef(quotation),
                    "<= " + rules.maxEstimatedValue, estimatedValue.toPlainString()));
        }

        int insuranceType = quotation.getVehicleInsuranceType() == null ? -1 : quotation.getVehicleInsuranceType();
        if (!rules.allowedVehicleInsuranceTypes.contains(insuranceType)) {
            errors.add(new ValidationError("VEHICLE_INSURANCE_TYPE", quotationRef(quotation),
                    String.valueOf(rules.allowedVehicleInsuranceTypes), String.valueOf(insuranceType)));
        }

        return errors;
    }

    private String quotationRef(QuotationResponse quotation) {
        return quotation.getQuotationNo() + "/" + quotation.getPlanCode();
    }
}
