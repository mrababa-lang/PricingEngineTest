package com.pricingengine.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.pricingengine.model.RuleConfig;

import java.util.ArrayList;
import java.util.List;

public class AgeValidator implements Validator {
    @Override
    public List<ValidationError> validate(JsonNode request, JsonNode quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        int age = readAge(request);
        if (age < config.customerRules.minAge || age > config.customerRules.maxAge) {
            errors.add(new ValidationError(
                    "AGE_RANGE",
                    quotationRef(quotation),
                    "%d <= age <= %d".formatted(config.customerRules.minAge, config.customerRules.maxAge),
                    String.valueOf(age)
            ));
        }
        return errors;
    }

    private int readAge(JsonNode request) {
        JsonNode age = request.path("Owner").path("Age");
        if (age.isMissingNode() || age.isNull()) {
            age = request.path("policyOwner").path("age");
        }
        return age.asInt(-1);
    }

    private String quotationRef(JsonNode quotation) {
        return quotation.path("quotationNo").asText("unknown") + "/" + quotation.path("planCode").asText("unknown");
    }
}
