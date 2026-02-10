package com.pricingengine.validator;

import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.api.request.PricingEngineRequest;
import com.pricingengine.model.api.response.QuotationResponse;

import java.util.ArrayList;
import java.util.List;

public class PlanValidator implements Validator {
    @Override
    public List<ValidationError> validate(PricingEngineRequest request, QuotationResponse quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        String planCode = quotation.getPlanCode();
        if (config.planRules != null && config.planRules.allowedPlanCodes != null
                && !config.planRules.allowedPlanCodes.contains(planCode)) {
            errors.add(new ValidationError("PLAN_ALLOWED", quotationRef(quotation),
                    String.valueOf(config.planRules.allowedPlanCodes), String.valueOf(planCode)));
        }
        return errors;
    }

    private String quotationRef(QuotationResponse quotation) {
        return quotation.getQuotationNo() + "/" + quotation.getPlanCode();
    }
}
