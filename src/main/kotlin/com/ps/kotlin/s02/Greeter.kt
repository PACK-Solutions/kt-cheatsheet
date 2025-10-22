package com.ps.kotlin.s02

interface Greeter {
    fun greet(name: String): String
    fun greetCasual(name: String): String = "Hi $name"
}

class ConsoleGreeter : Greeter {
    override fun greet(name: String): String = "Hello $name"
}

fun main() {
    val g: Greeter = ConsoleGreeter()
    println(g.greet("Alice"))       // Hello Alice
    println(g.greetCasual("Alice")) // Hi Alice
}