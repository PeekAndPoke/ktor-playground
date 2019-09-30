package de.peekandpoke.ktorfx.common

import de.peekandpoke.ultra.kontainer.Kontainer
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.sessions.CurrentSession
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext
import io.ultra.polyglot.I18n

// Registering the kontainer on call attributes

val KtorFX_Common = module {
    dynamic(CurrentSession::class, NullCurrentSession::class)
}

val KontainerKey = AttributeKey<Kontainer>("kontainer")

inline val ApplicationCall.kontainer: Kontainer get() = attributes[KontainerKey]
inline val PipelineContext<Unit, ApplicationCall>.kontainer: Kontainer get() = call.kontainer

fun Attributes.provide(value: Kontainer) = put(KontainerKey, value)


// TODO: remove this from here ... where to put it ?
inline val ApplicationCall.i18n: I18n get() = kontainer.get(I18n::class)
inline val PipelineContext<Unit, ApplicationCall>.i18n: I18n get() = call.i18n

