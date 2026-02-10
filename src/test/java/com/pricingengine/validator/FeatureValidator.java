package com.pricingengine.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.pricingengine.model.RuleConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FeatureValidator implements Validator {
    @Override
    public List<ValidationError> validate(JsonNode request, JsonNode quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        if (config.featureRules == null || config.featureRules.requiredFeatures == null) {
            return errors;
        }

        JsonNode features = quotation.path("features");
        for (RuleConfig.FeatureConstraint required : config.featureRules.requiredFeatures) {
            JsonNode found = null;
            if (features.isArray()) {
                for (JsonNode feature : features) {
                    if (required.code.equals(feature.path("code").asText())) {
                        found = feature;
                        break;
                    }
                }
            }
            if (found == null) {
                errors.add(new ValidationError("FEATURE_REQUIRED", quotationRef(quotation), required.code, "missing"));
                continue;
            }
            BigDecimal amount = found.path("amount").decimalValue();
            if (required.minAmount != null && amount.compareTo(required.minAmount) < 0) {
                errors.add(new ValidationError("FEATURE_MIN_AMOUNT", quotationRef(quotation),
                        required.code + ">=" + required.minAmount, amount.toPlainString()));
            }
            if (required.maxAmount != null && amount.compareTo(required.maxAmount) > 0) {
                errors.add(new ValidationError("FEATURE_MAX_AMOUNT", quotationRef(quotation),
                        required.code + "<=" + required.maxAmount, amount.toPlainString()));
            }
            if (required.payable != null && found.path("payable").asBoolean() != required.payable) {
                errors.add(new ValidationError("FEATURE_PAYABLE", quotationRef(quotation),
                        required.code + " payable=" + required.payable, String.valueOf(found.path("payable").asBoolean())));
            }
        }

        return errors;
    }

    private String quotationRef(JsonNode quotation) {
        return quotation.path("quotationNo").asText("unknown") + "/" + quotation.path("planCode").asText("unknown");
    }
}
