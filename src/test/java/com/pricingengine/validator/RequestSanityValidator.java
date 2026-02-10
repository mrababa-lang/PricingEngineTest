package com.pricingengine.validator;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class RequestSanityValidator {

    public static List<String> validate(JsonNode request) {
        List<String> issues = new ArrayList<>();
        if (!request.at("/policyOwner/age").canConvertToInt()) {
            issues.add("Missing or invalid request field: policyOwner.age");
        }
        if (!request.at("/vehicle/bodyCategoryCode").isTextual()) {
            issues.add("Missing or invalid request field: vehicle.bodyCategoryCode");
        }
        if (!request.at("/vehicle/manufactureYear").canConvertToInt()) {
            issues.add("Missing or invalid request field: vehicle.manufactureYear");
        }
        if (!request.at("/vehicle/estimatedValue").isNumber()) {
            issues.add("Missing or invalid request field: vehicle.estimatedValue");
        }
        return issues;
    }
}
