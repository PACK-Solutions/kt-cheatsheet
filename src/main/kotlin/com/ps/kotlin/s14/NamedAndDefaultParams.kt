package com.ps.kotlin.s14

/**
 * Paramètres nommés et valeurs par défaut en Kotlin
 */

data class Mail(
    val to: String,
    val subject: String = "(no subject)",
    val body: String = "",
    val urgent: Boolean = false
)

fun sendMail(mail: Mail): String {
    // Ici on retournerait un ID ou on enverrait réellement le mail.
    return "to=${mail.to}; subj=${mail.subject}; urgent=${mail.urgent}"
}

// Variante avec défauts directement dans la signature de fonction
fun greet(name: String, punctuation: String = "!", prefix: String = "Hello"):
        String = "$prefix $name$punctuation"

fun main() {
    // 1) Appels classiques (positionnels)
    println(greet("Alice"))                  // Hello Alice!
    println(greet("Bob", "?"))              // Hello Bob?

    // 2) Paramètres nommés: on peut changer l'ordre, expliciter, et sauter des valeurs
    println(greet(name = "Charly", prefix = "Hi"))      // Hi Charly!
    println(greet(punctuation = "!!!", name = "Dana"))   // Hello Dana!!!

    // 3) Objet avec valeurs par défaut + paramètres nommés
    val m1 = Mail(to = "team@example.com")
    val m2 = Mail(to = "boss@example.com", subject = "Weekly report", urgent = true)
    println(sendMail(m1))
    println(sendMail(m2))
}
