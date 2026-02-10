package com.pricingengine.model;

import java.math.BigDecimal;
import java.util.List;

public class RuleConfig {
    public String icCode;
    public CustomerRules customerRules;
    public VehicleRules vehicleRules;
    public PricingRules pricingRules;
    public PlanRules planRules;
    public FeatureRules featureRules;

    public static class CustomerRules {
        public Integer minAge;
        public Integer maxAge;
    }

    public static class VehicleRules {
        public List<Integer> allowedVehicleInsuranceTypes;
        public List<String> allowedBodyCategoryCodes;
        public Integer minManufactureYear;
        public Integer maxManufactureYear;
        public BigDecimal minEstimatedValue;
        public BigDecimal maxEstimatedValue;
    }

    public static class PricingRules {
        public BigDecimal minBasePremium;
        public BigDecimal maxBasePremium;
    }

    public static class PlanRules {
        public List<String> allowedPlanCodes;
    }

    public static class FeatureRules {
        public List<FeatureConstraint> requiredFeatures;
    }

    public static class FeatureConstraint {
        public String code;
        public BigDecimal minAmount;
        public BigDecimal maxAmount;
        public Boolean payable;
    }
}
