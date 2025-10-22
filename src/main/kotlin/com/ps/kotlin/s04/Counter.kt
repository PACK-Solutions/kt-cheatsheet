package com.ps.kotlin.s04

class Counter {
    var value: Int = 0
        set(v) {
            require(v >= 0) { "value must be >= 0" }
            field = v
        }
}

fun main() {
    val c = Counter()
    c.value = 10
    println(c.value) // 10
}