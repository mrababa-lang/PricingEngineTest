package com.pricingengine.validator;

import com.pricingengine.model.api.request.PricingEngineRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestSanityValidator {

    public static List<String> validate(PricingEngineRequest request) {
        List<String> issues = new ArrayList<>();

        if (request.getPolicyOwner() == null || request.getPolicyOwner().getAge() == null) {
            issues.add("Missing or invalid request field: policyOwner.age");
        }

        if (request.getVehicle() == null || request.getVehicle().getBodyCategoryCode() == null) {
            issues.add("Missing or invalid request field: vehicle.bodyCategoryCode");
        }

        if (request.getVehicle() == null || request.getVehicle().getManufactureYear() == null) {
            issues.add("Missing or invalid request field: vehicle.manufactureYear");
        }

        if (request.getVehicle() == null || request.getVehicle().getEstimatedValue() == null) {
            issues.add("Missing or invalid request field: vehicle.estimatedValue");
        }

        return issues;
    }
}
