package com.pricingengine.model.api.response;

import java.math.BigDecimal;

public class PremiumBreakdown {
    private BigDecimal vatAmount;
    private BigDecimal totalPremium;

    public BigDecimal getVatAmount() { return vatAmount; }
    public void setVatAmount(BigDecimal vatAmount) { this.vatAmount = vatAmount; }
    public BigDecimal getTotalPremium() { return totalPremium; }
    public void setTotalPremium(BigDecimal totalPremium) { this.totalPremium = totalPremium; }
}
