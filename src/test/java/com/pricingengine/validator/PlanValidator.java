package com.pricingengine.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.pricingengine.model.RuleConfig;

import java.util.ArrayList;
import java.util.List;

public class PlanValidator implements Validator {
    @Override
    public List<ValidationError> validate(JsonNode request, JsonNode quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        String planCode = quotation.path("planCode").asText("");
        if (config.planRules != null && config.planRules.allowedPlanCodes != null
                && !config.planRules.allowedPlanCodes.contains(planCode)) {
            errors.add(new ValidationError("PLAN_ALLOWED", quotationRef(quotation),
                    String.valueOf(config.planRules.allowedPlanCodes), planCode));
        }
        return errors;
    }

    private String quotationRef(JsonNode quotation) {
        return quotation.path("quotationNo").asText("unknown") + "/" + quotation.path("planCode").asText("unknown");
    }
}
