package com.pricingengine.validator;

public record ValidationError(String ruleId, String quotationRef, String expected, String actual) {
    @Override
    public String toString() {
        return "[%s] %s expected=%s actual=%s".formatted(ruleId, quotationRef, expected, actual);
    }
}
