package de.peekandpoke.modules.cms

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

fun KontainerBuilder.cmsCommon() = module(CmsCommonModule)

val CmsCommonModule = module {
    singleton(Cms::class)
    singleton(SemanticMarkdown::class)
}

/**
 * Shorthand for getting the Cms from the kontainer
 */
inline val ApplicationCall.cms: Cms get() = kontainer.get(Cms::class)

/**
 * Shorthand for getting the Cms from the kontainer
 */
inline val PipelineContext<Unit, ApplicationCall>.cms: Cms get() = call.cms


