package com.pricingengine.model.api.request;

import java.math.BigDecimal;

public final class PricingRequestFactory {
    private PricingRequestFactory() {
    }

    public static PricingEngineRequest createDefaultRequest() {
        PricingEngineRequest request = new PricingEngineRequest();

        Plan plan = new Plan();
        plan.setId("PLAN-ALAIN-BASIC");
        plan.setInsuranceCompanyId("ALAIN");
        plan.setCompanyCode("ALAIN");
        plan.setProductId("162");
        plan.setName("ALAIN Basic Plan");
        plan.setCode("A-BASIC");
        plan.setIsActive(true);
        plan.setIsDeleted(false);
        request.setPlan(plan);

        Vehicle vehicle = new Vehicle();
        vehicle.setIdentityTypeCode(1);
        vehicle.setMakeCode("3018");
        vehicle.setMake("FORD");
        vehicle.setModelCode("30405");
        vehicle.setModel("EDGE");
        vehicle.setBodyCategoryCode("SUV");
        vehicle.setManufactureYear(2021);
        vehicle.setNoOfPassengers(5);
        vehicle.setNoOfCylinders(6);
        vehicle.setRegistrationDate("2024-12-04");
        vehicle.setRegistrationAgeInMonths(14);
        vehicle.setEstimatedValue(new BigDecimal("73400.00"));
        vehicle.setUsageCode("1");
        vehicle.setFuelType("4");
        vehicle.setIsElectric(false);
        vehicle.setIsChinese(false);
        request.setVehicle(vehicle);

        PolicyOwner policyOwner = new PolicyOwner();
        policyOwner.setIdentityTypeCode(1);
        policyOwner.setOwnerIdentifier("784198960958027");
        policyOwner.setAge(38);
        policyOwner.setDateOfBirth("1987-02-20");
        policyOwner.setLicenseIssueAgeInYears(24);
        policyOwner.setLicenseIssueAgeInMonths(0);
        policyOwner.setNationalityCountryCode("46");
        request.setPolicyOwner(policyOwner);

        User user = new User();
        user.setId("TEST-USER");
        user.setName("Automation User");
        request.setUser(user);

        InspectionDetails inspectionDetails = new InspectionDetails();
        inspectionDetails.setPassed(true);
        inspectionDetails.setInspectedAt("2025-01-10T09:00:00+04:00");
        request.setInspectionDetails(inspectionDetails);

        VehicleValuation vehicleValuation = new VehicleValuation();
        vehicleValuation.setMarketValue(new BigDecimal("72000.00"));
        vehicleValuation.setCurrency("AED");
        request.setVehicleValuation(vehicleValuation);

        request.setCurrentInsuranceType("3");
        request.setClaimHistory(1);
        request.setIsRenewal(false);
        request.setHasAdditionalDrivers(false);
        request.setOverrideClaimsHistory(0);

        return request;
    }
}
