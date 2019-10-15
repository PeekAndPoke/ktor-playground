package de.peekandpoke.ktorfx.broker

import de.peekandpoke.ktorfx.broker.converters.*
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

fun KontainerBuilder.ktorFxBroker() = module(KtorFX_Broker)

/**
 * Broker kontainer module for type safe routing.
 */
val KtorFX_Broker = module {

    singleton(IncomingConverterLookup::class)
    dynamic(IncomingConverter::class)
    dynamic(IncomingVaultConverter::class)
    singleton(IncomingPrimitiveConverter::class)
    singleton(IncomingJavaTimeConverter::class)

    singleton(OutgoingConverter::class)
    singleton(OutgoingVaultConverter::class)
    singleton(OutgoingJavaTimeConverter::class)
}

/**
 * Makes the data converter for incoming data available on an [ApplicationCall]
 */
inline val ApplicationCall.incomingConverter: IncomingConverter get() = kontainer.get(IncomingConverter::class)

/**
 * Makes the data converter for incoming data available on an [ApplicationCall]
 */
inline val PipelineContext<Unit, ApplicationCall>.incomingConverter: IncomingConverter get() = call.incomingConverter


