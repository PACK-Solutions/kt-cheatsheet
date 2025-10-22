package com.ps.java21.s10;

import java.math.BigDecimal;

public record Amount(BigDecimal value, String currency) {
    public Amount {
        if (value == null || currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }
    public Amount plus(Amount other) {
        if (!currency.equals(other.currency)) throw new IllegalArgumentException("Currency mismatch");
        return new Amount(value.add(other.value), currency);
    }
    public Amount times(int factor) {
        return new Amount(value.multiply(BigDecimal.valueOf(factor)), currency);
    }
}
