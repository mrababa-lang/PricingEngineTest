package com.pricingengine.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.pricingengine.model.RuleConfig;

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

    public List<ValidationError> validateAll(JsonNode request, JsonNode responseArray, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        if (!responseArray.isArray()) {
            errors.add(new ValidationError("RESPONSE_SCHEMA", "response", "JSON array", responseArray.getNodeType().name()));
            return errors;
        }
        for (JsonNode quotation : responseArray) {
            for (Validator validator : validators) {
                errors.addAll(validator.validate(request, quotation, config));
            }
        }
        return errors;
    }
}
