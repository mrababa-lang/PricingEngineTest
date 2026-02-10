package com.pricingengine.validator;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class RequestSanityValidator {

    public static List<String> validate(JsonNode request) {
        List<String> issues = new ArrayList<>();

        JsonNode age = firstPresent(request.at("/Owner/Age"), request.at("/policyOwner/age"));
        if (!age.canConvertToInt()) {
            issues.add("Missing or invalid request field: Owner.Age");
        }

        JsonNode bodyCategory = firstPresent(request.at("/Car/BodyCategoryCode"), request.at("/vehicle/bodyCategoryCode"));
        if (!bodyCategory.isTextual()) {
            issues.add("Missing or invalid request field: Car.BodyCategoryCode");
        }

        JsonNode manufactureYear = firstPresent(request.at("/Car/ManufactureYear"), request.at("/vehicle/manufactureYear"));
        if (!manufactureYear.canConvertToInt()) {
            issues.add("Missing or invalid request field: Car.ManufactureYear");
        }

        JsonNode estimatedValue = firstPresent(request.at("/Car/EstimatedValue"), request.at("/vehicle/estimatedValue"));
        if (!estimatedValue.isNumber() && !estimatedValue.isTextual()) {
            issues.add("Missing or invalid request field: Car.EstimatedValue");
        }

        return issues;
    }

    private static JsonNode firstPresent(JsonNode primary, JsonNode fallback) {
        return !primary.isMissingNode() && !primary.isNull() ? primary : fallback;
    }
}
