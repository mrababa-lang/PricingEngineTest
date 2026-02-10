package com.pricingengine.model.api.response;

import java.math.BigDecimal;
import java.util.List;

public class QuotationResponse {
    private String quotationNo;
    private String planCode;
    private Integer vehicleInsuranceType;
    private BigDecimal consideredCarValue;
    private BigDecimal basePremium;
    private BigDecimal policyPremiumWithoutVat;
    private List<FeatureResponse> features;
    private List<DiscountResponse> discounts;
    private PremiumBreakdown premiumBreakdown;

    public String getQuotationNo() { return quotationNo; }
    public void setQuotationNo(String quotationNo) { this.quotationNo = quotationNo; }
    public String getPlanCode() { return planCode; }
    public void setPlanCode(String planCode) { this.planCode = planCode; }
    public Integer getVehicleInsuranceType() { return vehicleInsuranceType; }
    public void setVehicleInsuranceType(Integer vehicleInsuranceType) { this.vehicleInsuranceType = vehicleInsuranceType; }
    public BigDecimal getConsideredCarValue() { return consideredCarValue; }
    public void setConsideredCarValue(BigDecimal consideredCarValue) { this.consideredCarValue = consideredCarValue; }
    public BigDecimal getBasePremium() { return basePremium; }
    public void setBasePremium(BigDecimal basePremium) { this.basePremium = basePremium; }
    public BigDecimal getPolicyPremiumWithoutVat() { return policyPremiumWithoutVat; }
    public void setPolicyPremiumWithoutVat(BigDecimal policyPremiumWithoutVat) { this.policyPremiumWithoutVat = policyPremiumWithoutVat; }
    public List<FeatureResponse> getFeatures() { return features; }
    public void setFeatures(List<FeatureResponse> features) { this.features = features; }
    public List<DiscountResponse> getDiscounts() { return discounts; }
    public void setDiscounts(List<DiscountResponse> discounts) { this.discounts = discounts; }
    public PremiumBreakdown getPremiumBreakdown() { return premiumBreakdown; }
    public void setPremiumBreakdown(PremiumBreakdown premiumBreakdown) { this.premiumBreakdown = premiumBreakdown; }
}
