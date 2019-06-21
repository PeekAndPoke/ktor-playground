package de.peekandpoke.z_appimpl

import de.peekandpoke.common.I18n
import de.peekandpoke.common.Texts
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.AttributeKey
import io.ktor.util.pipeline.PipelineContext

val iocI18n = AttributeKey<I18n>("i18n")

val PipelineContext<Unit, ApplicationCall>.t : I18n get() {

    println(call.attributes.allKeys)

    return call.attributes[iocI18n]
}

val Translations = I18n(
    "de", "en", mapOf(
        "de" to Texts(
            mapOf(
                "WELCOME" to "Herzlich willkommen!",
                "WELCOME_NAME" to "Herzlich willkommen %name%!"
            )
        ),
        "en" to Texts(
            mapOf(
                "WELCOME" to "Welcome!",
                "WELCOME_NAME" to "Welcome %name%!"
            )
        )
    )
)

fun I18n.WELCOME() = get("WELCOME")
fun I18n.WELCOME_NAME(name: String) = get("WELCOME", "%name%" to name)

