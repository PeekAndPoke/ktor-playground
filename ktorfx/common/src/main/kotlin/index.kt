package de.peekandpoke.ktorfx.common

import de.peekandpoke.ultra.kontainer.Kontainer
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext

// Registering the kontainer on call attributes

val KontainerKey = AttributeKey<Kontainer>("kontainer")

inline val ApplicationCall.kontainer: Kontainer get() = attributes[KontainerKey]
inline val PipelineContext<Unit, ApplicationCall>.kontainer: Kontainer get() = call.kontainer

fun Attributes.provide(value: Kontainer) = put(KontainerKey, value)



