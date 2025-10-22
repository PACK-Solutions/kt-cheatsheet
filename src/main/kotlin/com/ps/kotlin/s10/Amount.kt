package com.ps.kotlin.s10

import java.math.BigDecimal

data class Amount(
    val value: BigDecimal,
    val currency: String
) {
    init {
        require(currency.isNotBlank()) { "currency required" }
    }

    operator fun plus(other: Amount): Amount {
        require(currency == other.currency) { "Currency mismatch: $currency vs ${other.currency}" }
        return copy(value = value + other.value)
    }

    operator fun times(factor: Int): Amount =
        copy(value = value * factor.toBigDecimal())

    fun compareTo(other: Amount): Int {
        require(currency == other.currency) { "Currency mismatch" }
        return value.compareTo(other.value)
    }
}

// Extensions BigDecimal pour opérateurs
private operator fun BigDecimal.plus(other: BigDecimal): BigDecimal = this.add(other)
private operator fun BigDecimal.times(other: BigDecimal): BigDecimal = this.multiply(other)

fun main() {
    // Attention au séparateur décimal selon locale: BigDecimal attend un point
    val a = Amount(BigDecimal("10.50"), "EUR")
    val b = Amount(BigDecimal("2"), "EUR")

    val total = a + b     // operator plus
    val doubled = total * 2

    println(total)        // Amount(value=12.50, currency=EUR)
    println(doubled)      // Amount(value=25.00, currency=EUR)
}