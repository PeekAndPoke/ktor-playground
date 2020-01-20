package de.peekandpoke.module.cms

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.module.cms.elements.*
import de.peekandpoke.module.cms.layouts.LandingPageLayout
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

fun KontainerBuilder.cmsCommon() = module(CmsCommonModule)

val CmsCommonModule = module {
    singleton0(Cms::class) {
        Cms(
            // available layouts
            mapOf(
                CmsLayout.Empty::class to CmsLayout.Empty,
                LandingPageLayout::class to LandingPageLayout.Empty
            ),
            // available elements
            mapOf(
                DividerElement::class to DividerElement(),
                FooterElement::class to FooterElement(),
                GalleryElement::class to GalleryElement(),
                HeroElement::class to HeroElement(),
                ListElement::class to ListElement(),
                TextElement::class to TextElement(),
                TextImageElement::class to TextImageElement()
            )
        )
    }
}

/**
 * Shorthand for getting the Cms from the kontainer
 */
inline val ApplicationCall.cms: Cms get() = kontainer.get(Cms::class)

/**
 * Shorthand for getting the Cms from the kontainer
 */
inline val PipelineContext<Unit, ApplicationCall>.cms: Cms get() = call.cms


