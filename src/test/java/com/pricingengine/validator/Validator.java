package com.pricingengine.validator;

import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.api.request.PricingEngineRequest;
import com.pricingengine.model.api.response.QuotationResponse;

import java.util.List;

public interface Validator {
    List<ValidationError> validate(PricingEngineRequest request, QuotationResponse quotation, RuleConfig config);
}
