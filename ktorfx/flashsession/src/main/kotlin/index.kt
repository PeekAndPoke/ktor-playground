package de.peekandpoke.ktorfx.flashsession

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.sessions.sessions
import io.ktor.util.pipeline.PipelineContext

// TODO: provide via container
val PipelineContext<Unit, ApplicationCall>.flashSession get() = FlashSession(call.sessions)
