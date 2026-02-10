package com.pricingengine.validator;

import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.api.request.PricingEngineRequest;
import com.pricingengine.model.api.response.QuotationResponse;

import java.util.ArrayList;
import java.util.List;

public class RuleValidationEngine {
    private final List<Validator> validators;

    public RuleValidationEngine() {
        this.validators = List.of(
                new QuotationSchemaValidator(),
                new AgeValidator(),
                new VehicleValidator(),
                new PremiumValidator(),
                new PlanValidator(),
                new FeatureValidator()
        );
    }

    public List<ValidationError> validateAll(PricingEngineRequest request, List<QuotationResponse> quotations, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        if (quotations == null || quotations.isEmpty()) {
            errors.add(new ValidationError("RESPONSE_SCHEMA", "response", "non-empty quotation list", "empty"));
            return errors;
        }
        for (QuotationResponse quotation : quotations) {
            for (Validator validator : validators) {
                errors.addAll(validator.validate(request, quotation, config));
            }
        }
        return errors;
    }
}
