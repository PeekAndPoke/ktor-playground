package com.thebase.apps.cms

import com.thebase.apps.cms.elements.*
import com.thebase.apps.cms.layouts.LandingPageLayout
import de.peekandpoke.modules.cms.Cms

class TheBaseCmsModule : Cms.Module(
    // available layouts
    layouts = mapOf(
        LandingPageLayout::class to LandingPageLayout.Empty
    ),
    // available elements
    elements = mapOf(
        DividerElement::class to DividerElement(),
        FooterElement::class to FooterElement(),
        GalleryElement::class to GalleryElement(),
        HeroElement::class to HeroElement(),
        ListElement::class to ListElement(),
        SnippetElement::class to SnippetElement(),
        TextElement::class to TextElement(),
        TextImageElement::class to TextImageElement()
    )
)
