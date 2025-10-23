package com.ps.kotlin.s12

// Exemple de destructuration en Kotlin
// - Pair et Triple (via componentN)
// - data class (componentN générés automatiquement)
// - Entries de Map
// - Ignorer des composants avec _

data class Person(val name: String, val age: Int)

data class HttpResult(val code: Int, val body: String?)

fun compute(): Pair<Int, Int> = 21 to 21

fun httpCall(ok: Boolean): HttpResult = if (ok) HttpResult(200, "{...}") else HttpResult(500, null)

fun main() {
    // 1) Pair
    val (x, y) = compute() // destructuration de Pair
    println("x=$x, y=$y")

    // 2) data class
    val p = Person("Alice", 30)
    val (n, a) = p // component1 -> name, component2 -> age
    println("$n a $a ans")

    // 3) Map.Entry
    val frequencies = mapOf("kotlin" to 3, "java" to 2)
    for ((word, count) in frequencies) {
        println("$word → $count")
    }

    // 4) Ignorer un composant avec _
    val (_, justAge) = p
    println("only age = $justAge")

    // 5) Destructurer dans un let pour limiter la portée
    httpCall(ok = true).let { (code, body) ->
        println("HTTP $code, body size = ${body?.length ?: 0}")
    }
}
