package com.ps.kotlin.s09

// Infix d'extension pour créer une Pair lisible
infix fun <A, B> A.toPair(b: B): Pair<A, B> = Pair(this, b)

// Infix métier d'exemple: appliquer un pourcentage (taux) à une valeur entière
infix fun Int.percentOf(base: Int): Int = (base * this) / 100

fun main() {
    val p = "EUR" toPair 100            // infix personnalisé pour éviter conflit avec standard to
    val discount = 15 percentOf 200    // 15% de 200 = 30

    println(p)         // (EUR, 100)
    println(discount)  // 30
}