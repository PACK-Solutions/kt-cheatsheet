package com.ps.kotlin.s07

// Hiérarchie fermée: sous-types limités
sealed interface Result<out T> {
    data class Success<T>(val value: T) : Result<T>
    data class Error(val message: String, val cause: Throwable? = null) : Result<Nothing>
}

fun <T> render(r: Result<T>): String =
    when (r) {
        is Result.Success -> "OK: ${r.value}"
        is Result.Error   -> "ERR: ${r.message}"
        // Pas besoin de else: when exhaustif grâce à sealed
    }

fun main() {
    val ok: Result<String> = Result.Success("data")
    val ko: Result<String> = Result.Error("boom")

    println(render(ok))
    println(render(ko))
}