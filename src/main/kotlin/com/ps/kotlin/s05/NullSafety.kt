package com.ps.kotlin.s05

fun safeUpper(s: String?): String {
    // Opérateur sûr + Elvis
    return s?.uppercase() ?: "N/A"
}

fun requireNonNullUpper(s: String?): String {
    // requireNotNull -> échoue tôt avec message clair
    val nonNull = requireNotNull(s) { "s must not be null" }
    return nonNull.uppercase()
}

fun main() {
    val a: String? = null
    val b: String? = "hello"

    println(safeUpper(a))        // N/A
    println(safeUpper(b))        // HELLO

    // Utilisation idiomatique avec let
    b?.let { println("Length = ${it.length}") }

    // Éviter !! sauf si vous avez une garantie contractuelle
    // val crash = a!!.length // NPE si dé-commenté
}