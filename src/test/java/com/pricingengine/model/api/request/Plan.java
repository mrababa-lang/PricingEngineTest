package com.pricingengine.model.api.request;

public class Plan {
    private String id;
    private String insuranceCompanyId;
    private String companyCode;
    private String productId;
    private String name;
    private String code;
    private Boolean isActive;
    private Boolean isDeleted;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getInsuranceCompanyId() { return insuranceCompanyId; }
    public void setInsuranceCompanyId(String insuranceCompanyId) { this.insuranceCompanyId = insuranceCompanyId; }
    public String getCompanyCode() { return companyCode; }
    public void setCompanyCode(String companyCode) { this.companyCode = companyCode; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { isActive = active; }
    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean deleted) { isDeleted = deleted; }
}
