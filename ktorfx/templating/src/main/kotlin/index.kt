package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

/**
 * Template kontainer module
 */
val KtorFX_Templating = module {

    /**
     * Overwrite this one in with an application specific implementation of [SimpleTemplate]
     */
    prototype(SimpleTemplate::class, SimpleTemplateImpl::class)
}

inline val ApplicationCall.defaultTemplate: SimpleTemplate get() = kontainer.get(SimpleTemplate::class)
inline val PipelineContext<Unit, ApplicationCall>.defaultTemplate: SimpleTemplate get() = call.defaultTemplate
