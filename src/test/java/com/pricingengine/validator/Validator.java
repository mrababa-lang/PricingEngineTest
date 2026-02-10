package com.pricingengine.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.pricingengine.model.RuleConfig;

import java.util.List;

public interface Validator {
    List<ValidationError> validate(JsonNode request, JsonNode quotation, RuleConfig config);
}
