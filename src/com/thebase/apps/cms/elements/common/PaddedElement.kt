package com.thebase.apps.cms.elements.common

import de.peekandpoke.ktorfx.semanticui.SemanticUi

interface PaddedElement {
    val padding: ElementPadding

    fun SemanticUi.withPadding(): SemanticUi =
        given(!padding.paddingTop) { with("attach-top") }
            .given(!padding.paddingBottom) { with("attach-bottom") }
}
