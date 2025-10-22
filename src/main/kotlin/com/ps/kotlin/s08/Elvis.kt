package com.ps.kotlin.s08

fun displayName(nickname: String?, fullName: String?): String =
    nickname ?: fullName ?: "Unknown"

fun required(input: String?): String =
    input ?: throw IllegalArgumentException("input required")

fun lookupOrCompute(cache: Map<String, String>, key: String, supplier: () -> String): String {
    // Elvis avec appel paresseux via supplier
    return cache[key] ?: supplier()
}

fun main() {
    println(displayName(null, "Alice"))     // Alice
    println(displayName(null, null))        // Unknown
    println(required("ok"))                 // ok

    val cache = mapOf("k" to "v")
    println(lookupOrCompute(cache, "k") { "computed" })    // v
    println(lookupOrCompute(cache, "x") { "computed" })    // computed
}