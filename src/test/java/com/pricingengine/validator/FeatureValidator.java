package com.pricingengine.validator;

import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.api.request.PricingEngineRequest;
import com.pricingengine.model.api.response.FeatureResponse;
import com.pricingengine.model.api.response.QuotationResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FeatureValidator implements Validator {
    @Override
    public List<ValidationError> validate(PricingEngineRequest request, QuotationResponse quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        if (config.featureRules == null || config.featureRules.requiredFeatures == null) {
            return errors;
        }

        List<FeatureResponse> features = quotation.getFeatures();
        for (RuleConfig.FeatureConstraint required : config.featureRules.requiredFeatures) {
            FeatureResponse found = null;
            if (features != null) {
                for (FeatureResponse feature : features) {
                    if (required.code.equals(feature.getCode())) {
                        found = feature;
                        break;
                    }
                }
            }
            if (found == null) {
                errors.add(new ValidationError("FEATURE_REQUIRED", quotationRef(quotation), required.code, "missing"));
                continue;
            }
            BigDecimal amount = found.getAmount() == null ? BigDecimal.ZERO : found.getAmount();
            if (required.minAmount != null && amount.compareTo(required.minAmount) < 0) {
                errors.add(new ValidationError("FEATURE_MIN_AMOUNT", quotationRef(quotation),
                        required.code + ">=" + required.minAmount, amount.toPlainString()));
            }
            if (required.maxAmount != null && amount.compareTo(required.maxAmount) > 0) {
                errors.add(new ValidationError("FEATURE_MAX_AMOUNT", quotationRef(quotation),
                        required.code + "<=" + required.maxAmount, amount.toPlainString()));
            }
            if (required.payable != null && !required.payable.equals(found.getPayable())) {
                errors.add(new ValidationError("FEATURE_PAYABLE", quotationRef(quotation),
                        required.code + " payable=" + required.payable, String.valueOf(found.getPayable())));
            }
        }

        return errors;
    }

    private String quotationRef(QuotationResponse quotation) {
        return quotation.getQuotationNo() + "/" + quotation.getPlanCode();
    }
}
