package de.peekandpoke.ktorfx.common

import de.peekandpoke.ultra.depot.Depot
import de.peekandpoke.ultra.kontainer.Kontainer
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.polyglot.I18n
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.sessions.CurrentSession
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext

/**
 * Common kontainer module
 */
val KtorFX_Common = module {
    dynamic(CurrentSession::class, NullCurrentSession::class)
}

/**
 * [AttributeKey] for the [Kontainer]
 */
val KontainerKey = AttributeKey<Kontainer>("kontainer")

/**
 * Puts a [Kontainer] instance into an [Attributes] set
 */
fun Attributes.provide(value: Kontainer) = put(KontainerKey, value)

/**
 * Checks if the kontainer is in the attributes
 */
inline val ApplicationCall.hasKontainer: Boolean get() = attributes.contains(KontainerKey)

/**
 * Checks if the kontainer is in the attributes
 */
inline val PipelineContext<Unit, ApplicationCall>.hasKontainer: Boolean get() = call.hasKontainer

/**
 * Retrieves the [Kontainer] from the [Attributes] of an [ApplicationCall]
 */
inline val ApplicationCall.kontainer: Kontainer get() = attributes[KontainerKey]

/**
 * Retrieves the [Kontainer] from the [Attributes] of an [ApplicationCall]
 */
inline val PipelineContext<Unit, ApplicationCall>.kontainer: Kontainer get() = call.kontainer


// TODO: remove this from here ... where to put it? -> ktorfx::polyglot
inline val ApplicationCall.i18n: I18n get() = kontainer.get(I18n::class)
inline val PipelineContext<Unit, ApplicationCall>.i18n: I18n get() = call.i18n

// TODO: remove this from here ... where to put it? -> ktorfx::depot
inline val ApplicationCall.depot get() = kontainer.get(Depot::class)
inline val PipelineContext<Unit, ApplicationCall>.depot get() = kontainer.get(Depot::class)
