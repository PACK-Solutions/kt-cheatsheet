package com.ps.kotlin.s01

data class User(
    val name: String,
    val age: Int
)

fun main() {
    val u1 = User("Alice", 30)
    val u2 = u1.copy(age = 31)

    println(u1)               // User(name=Alice, age=30)
    println(u2 == u1)         // false
    val (n, a) = u2           // destructuration
    println("$n a $a ans")
}