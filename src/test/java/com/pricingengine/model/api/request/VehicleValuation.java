package com.pricingengine.model.api.request;

import java.math.BigDecimal;

public class VehicleValuation {
    private BigDecimal marketValue;
    private String currency;

    public BigDecimal getMarketValue() { return marketValue; }
    public void setMarketValue(BigDecimal marketValue) { this.marketValue = marketValue; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
