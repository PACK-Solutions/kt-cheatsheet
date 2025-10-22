package com.ps.kotlin.s11

data class User(val id: Long, val email: String?)

fun findUser(id: Long): User? =
    if (id % 2L == 0L) User(id, "user$id@exemple.fr") else null

fun sendEmail(to: String, subject: String, body: String) {
    println("Envoi email à $to: $subject\n$body")
}

fun main() {
    val user = findUser(42)

    // 1) Null-safety: exécuter seulement si non nul
    user?.email?.let { email ->
        sendEmail(email, subject = "Bienvenue", body = "Bonjour et bienvenue !")
    }

    // 2) Transformation avec let: retourne la dernière expression
    val domain = user?.email
        ?.let { it.substringAfter('@') }
        ?: "domaine-inconnu"
    println("Domaine: $domain")

    // 3) Limiter la portée: variable temporaire confinée
    val tokens = "a,b,c"
        .split(',')
        .let { parts ->
            // parts: List<String> visible uniquement ici
            parts.map { it.trim() }.filter { it.isNotEmpty() }
        }
    println(tokens)

    // 4) let pour logging ciblé tout en continuant le chaînage
    val lengthOrZero = user?.email
        ?.let { it.uppercase() }
        ?.also { println("Uppercase email: $it") } // also retourne l'objet, pas la longueur
        ?.let { it.length }
        ?: 0
    println(lengthOrZero)

    // Exemples supplémentaires per README commentary
    val list = mutableListOf(1, 2, 3)
        .also { println("Avant: $it") }
        .apply { add(4) }        // apply: configure, retourne la liste
        .also { println("Après:  $it") }

    val result = StringBuilder().run {
        append("Hello")
        append(" ")
        append("Kotlin")
        length // retourne la longueur
    }

    val text = with(StringBuilder()) {
        append("Scoped ")
        append("with")
        toString() // retourne le résultat
    }

    println(list)
    println(result)
    println(text)
}