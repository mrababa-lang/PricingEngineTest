package com.pricingengine.model.api.response;

import java.math.BigDecimal;

public class FeatureResponse {
    private String code;
    private BigDecimal amount;
    private Boolean payable;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public Boolean getPayable() { return payable; }
    public void setPayable(Boolean payable) { this.payable = payable; }
}
