package de.peekandpoke.resources

import de.peekandpoke.ktorfx.formidable.Formidable
import io.ultra.polyglot.I18n
import io.ultra.polyglot.buildI18n

val Translations = buildI18n(
    "de", "en",
    // Formidable texts
    Formidable.loadI18n(),
    // Application texts
    mapOf(
        "de" to mapOf(
            "WELCOME" to "Herzlich willkommen!",
            "WELCOME_NAME" to "Herzlich willkommen %name%!"
        ),
        "en" to mapOf(
            "WELCOME" to "Welcome!",
            "WELCOME_NAME" to "Welcome %name%!"
        )
    )
)

fun I18n.WELCOME() = get("WELCOME")
fun I18n.WELCOME_NAME(name: String) = get("WELCOME", "name" to name)

