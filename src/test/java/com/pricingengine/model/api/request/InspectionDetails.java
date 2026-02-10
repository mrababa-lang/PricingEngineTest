package com.pricingengine.model.api.request;

public class InspectionDetails {
    private Boolean passed;
    private String inspectedAt;

    public Boolean getPassed() { return passed; }
    public void setPassed(Boolean passed) { this.passed = passed; }
    public String getInspectedAt() { return inspectedAt; }
    public void setInspectedAt(String inspectedAt) { this.inspectedAt = inspectedAt; }
}
