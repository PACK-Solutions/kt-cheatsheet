package com.ps.java8.s10;

import java.math.BigDecimal;
import java.util.Objects;

public final class Amount {
    private final BigDecimal value;
    private final String currency;

    public Amount(BigDecimal value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal value() { return value; }
    public String currency() { return currency; }

    public Amount plus(Amount other) {
        if (!Objects.equals(currency, other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Amount(value.add(other.value), currency);
    }

    public Amount times(int factor) {
        return new Amount(value.multiply(BigDecimal.valueOf(factor)), currency);
    }

    @Override public String toString() {
        return value + " " + currency;
    }
}
