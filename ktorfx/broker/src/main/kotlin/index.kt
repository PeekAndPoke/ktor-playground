package de.peekandpoke.ktorfx.broker

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
    singleton(IncomingConverter::class)
    singleton(IncomingPrimitiveConverter::class)
    singleton(IncomingVaultConverter::class)

    singleton(OutgoingConverter::class)
    singleton(OutgoingVaultConverter::class)
}

/**
 * Makes the data converter for incoming data available on an [ApplicationCall]
 */
inline val ApplicationCall.incomingConverter: IncomingConverter get() = kontainer.get(IncomingConverter::class)

/**
 * Makes the data converter for incoming data available on an [ApplicationCall]
 */
inline val PipelineContext<Unit, ApplicationCall>.incomingConverter: IncomingConverter get() = call.incomingConverter


