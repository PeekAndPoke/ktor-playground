package de.peekandpoke.module.cms

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.module.cms.elements.Cms
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

fun KontainerBuilder.cmsCommon() = module(CmsCommonModule)

val CmsCommonModule = module {

    singleton(Cms::class)

    // register cms layouts
    singleton(LandingPageLayout::class)

    // register cms elements
    singleton(GreetAllElement::class)
    singleton(GreetElement::class)
}

/**
 * Shorthand for getting the Cms from the kontainer
 */
inline val ApplicationCall.cms: Cms get() = kontainer.get(Cms::class)

/**
 * Shorthand for getting the Cms from the kontainer
 */
inline val PipelineContext<Unit, ApplicationCall>.cms: Cms get() = call.cms


