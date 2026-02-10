package com.pricingengine.validator;

import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.api.request.PricingEngineRequest;
import com.pricingengine.model.api.response.QuotationResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PremiumValidator implements Validator {
    @Override
    public List<ValidationError> validate(PricingEngineRequest request, QuotationResponse quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();

        BigDecimal basePremium = quotation.getBasePremium() == null ? BigDecimal.ZERO : quotation.getBasePremium();
        if (basePremium.compareTo(BigDecimal.ZERO) < 0) {
            errors.add(new ValidationError("BASE_PREMIUM_NON_NEGATIVE", quotationRef(quotation), ">= 0", basePremium.toPlainString()));
        }
        RuleConfig.PricingRules rules = config.pricingRules;
        if (rules != null && rules.minBasePremium != null && basePremium.compareTo(rules.minBasePremium) < 0) {
            errors.add(new ValidationError("BASE_PREMIUM_MIN", quotationRef(quotation), ">= " + rules.minBasePremium, basePremium.toPlainString()));
        }
        if (rules != null && rules.maxBasePremium != null && basePremium.compareTo(rules.maxBasePremium) > 0) {
            errors.add(new ValidationError("BASE_PREMIUM_MAX", quotationRef(quotation), "<= " + rules.maxBasePremium, basePremium.toPlainString()));
        }

        BigDecimal policyPremiumWithoutVat = quotation.getPolicyPremiumWithoutVat() == null
                ? BigDecimal.ZERO : quotation.getPolicyPremiumWithoutVat();
        if (policyPremiumWithoutVat.compareTo(BigDecimal.ZERO) < 0) {
            errors.add(new ValidationError("POLICY_PREMIUM_NON_NEGATIVE", quotationRef(quotation), ">= 0", policyPremiumWithoutVat.toPlainString()));
        }
        return errors;
    }

    private String quotationRef(QuotationResponse quotation) {
        return quotation.getQuotationNo() + "/" + quotation.getPlanCode();
    }
}
