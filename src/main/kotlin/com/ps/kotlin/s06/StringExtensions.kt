package com.ps.kotlin.s06

// Extension function sur String
fun String.surround(prefix: String, suffix: String): String = "$prefix$this$suffix"

// Extension property (lecture seule) d'exemple
val String.middle: Char?
    get() = if (isNotEmpty()) this[length / 2] else null

fun main() {
    println("core".surround("[", "]")) // [core]
    println("abc".middle)              // b
    println("".middle)                 // null
}