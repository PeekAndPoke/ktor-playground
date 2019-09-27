package de.peekandpoke.ktorfx.broker

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

val KtorFX_Broker = module {

    singleton(IncomingConverter::class)
    singleton(IncomingVaultConverter::class)

    singleton(OutgoingConverter::class)
    singleton(OutgoingVaultConverter::class)
}

inline val ApplicationCall.incomingConverter: IncomingConverter get() = kontainer.get(IncomingConverter::class)
inline val PipelineContext<Unit, ApplicationCall>.incomingConverter: IncomingConverter get() = call.incomingConverter


