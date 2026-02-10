package com.pricingengine.validator;

import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.api.request.PricingEngineRequest;
import com.pricingengine.model.api.response.QuotationResponse;

import java.util.ArrayList;
import java.util.List;

public class AgeValidator implements Validator {
    @Override
    public List<ValidationError> validate(PricingEngineRequest request, QuotationResponse quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        int age = request.getPolicyOwner() != null && request.getPolicyOwner().getAge() != null
                ? request.getPolicyOwner().getAge() : -1;
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

    private String quotationRef(QuotationResponse quotation) {
        return quotation.getQuotationNo() + "/" + quotation.getPlanCode();
    }
}
