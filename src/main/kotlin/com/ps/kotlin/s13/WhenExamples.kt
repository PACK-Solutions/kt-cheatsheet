package com.ps.kotlin.s13

// Démonstration des usages de `when` en Kotlin
// - `when` comme expression qui retourne une valeur
// - matching par valeur, par plage (in), et par type (is) avec smart casts
// - exhaustivité sur une hiérarchie scellée

sealed interface Shape

data class Circle(val radius: Double) : Shape

data class Rectangle(val width: Double, val height: Double) : Shape

data class Unknown(val label: String) : Shape

fun area(shape: Shape): Double = when (shape) {
    is Circle -> Math.PI * shape.radius * shape.radius // smart cast
    is Rectangle -> shape.width * shape.height
    is Unknown -> Double.NaN // exhaustif grâce à sealed interface
}

fun classifyScore(score: Int): String = when (score) {
    in 90..100 -> "A"
    in 75..89 -> "B"
    in 60..74 -> "C"
    in 0..59 -> "D"
    else -> "Invalide"
}

fun fizzBuzz(n: Int): String = when {
    n % 15 == 0 -> "FizzBuzz"
    n % 3 == 0 -> "Fizz"
    n % 5 == 0 -> "Buzz"
    else -> n.toString()
}

fun describe(x: Any?): String = when (x) {
    null -> "null"
    is String -> "String(len=${x.length})"
    is Number -> "Number($x)"
    else -> "Autre(${x::class.simpleName})"
}

fun main() {
    // 1) when + sealed: exhaustif et expressif
    val shapes: List<Shape> = listOf(Circle(2.0), Rectangle(3.0, 4.0), Unknown("?") )
    shapes.forEach { s -> println("area(${s}) = ${area(s)}") }

    // 2) when avec plages
    println((listOf(95, 80, 67, 10, -1)).map(::classifyScore))

    // 3) when sans sujet: garde booléenne
    println((1..16).joinToString(" ") { fizzBuzz(it) })

    // 4) when par type
    println(describe("kotlin"))
    println(describe(42))
    println(describe(null))
}
