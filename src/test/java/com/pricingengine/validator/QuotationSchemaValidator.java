package com.pricingengine.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.pricingengine.model.RuleConfig;

import java.util.ArrayList;
import java.util.List;

public class QuotationSchemaValidator implements Validator {
    private static final String[] REQUIRED_FIELDS = {
            "quotationNo", "planCode", "vehicleInsuranceType", "basePremium", "policyPremiumWithoutVat"
    };

    @Override
    public List<ValidationError> validate(JsonNode request, JsonNode quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        for (String field : REQUIRED_FIELDS) {
            if (!quotation.hasNonNull(field)) {
                errors.add(new ValidationError("MANDATORY_FIELD", quotationRef(quotation), field + " present", "missing"));
            }
        }
        return errors;
    }

    private String quotationRef(JsonNode quotation) {
        return quotation.path("quotationNo").asText("unknown") + "/" + quotation.path("planCode").asText("unknown");
    }
}
