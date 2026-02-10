package com.pricingengine.model.api.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PricingEngineRequest {
    private Plan plan;
    private Vehicle vehicle;
    @JsonProperty("policyOwner")
    @JsonAlias("Owner")
    private PolicyOwner policyOwner;
    private User user;
    private InspectionDetails inspectionDetails;
    private VehicleValuation vehicleValuation;
    private String currentInsuranceType;
    private Integer claimHistory;
    private Boolean isRenewal;
    private Boolean hasAdditionalDrivers;
    private Integer overrideClaimsHistory;

    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public PolicyOwner getPolicyOwner() { return policyOwner; }
    public void setPolicyOwner(PolicyOwner policyOwner) { this.policyOwner = policyOwner; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public InspectionDetails getInspectionDetails() { return inspectionDetails; }
    public void setInspectionDetails(InspectionDetails inspectionDetails) { this.inspectionDetails = inspectionDetails; }

    public VehicleValuation getVehicleValuation() { return vehicleValuation; }
    public void setVehicleValuation(VehicleValuation vehicleValuation) { this.vehicleValuation = vehicleValuation; }

    public String getCurrentInsuranceType() { return currentInsuranceType; }
    public void setCurrentInsuranceType(String currentInsuranceType) { this.currentInsuranceType = currentInsuranceType; }

    public Integer getClaimHistory() { return claimHistory; }
    public void setClaimHistory(Integer claimHistory) { this.claimHistory = claimHistory; }

    public Boolean getIsRenewal() { return isRenewal; }
    public void setIsRenewal(Boolean renewal) { isRenewal = renewal; }

    public Boolean getHasAdditionalDrivers() { return hasAdditionalDrivers; }
    public void setHasAdditionalDrivers(Boolean hasAdditionalDrivers) { this.hasAdditionalDrivers = hasAdditionalDrivers; }

    public Integer getOverrideClaimsHistory() { return overrideClaimsHistory; }
    public void setOverrideClaimsHistory(Integer overrideClaimsHistory) { this.overrideClaimsHistory = overrideClaimsHistory; }
}
