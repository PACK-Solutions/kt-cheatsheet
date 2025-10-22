package com.ps.kotlin.s03

inline fun <T, R> withValue(value: T, block: (T) -> R): R = block(value)

fun main() {
    // Sans trailing lambda
    val r1 = withValue(42, { v -> "val=$v" })

    // Avec trailing lambda (idiomatique)
    val r2 = withValue(42) { v -> "val=$v" }

    // Type inference avec it
    val r3 = withValue(42) { "val=$it" }

    println(r1)
    println(r2)
    println(r3)
}