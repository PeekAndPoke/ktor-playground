package de.peekandpoke.ktorfx.flashsession

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

/**
 * FlashSession kontainer module
 *
 * Defines the following dynamic services:
 *
 * - [FlashSession] which defaults to [NullFlashSession]
 */
val KtorFX_FlashSession = module {

    dynamic(FlashSession::class) { NullFlashSession() }
}

inline val ApplicationCall.flashSession: FlashSession get() = kontainer.get(FlashSession::class)
inline val PipelineContext<Unit, ApplicationCall>.flashSession: FlashSession get() = call.flashSession
