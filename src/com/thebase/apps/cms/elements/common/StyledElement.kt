package com.thebase.apps.cms.elements.common

import de.peekandpoke.ktorfx.semanticui.SemanticUi


interface StyledElement {
    val styling: ElementStyle

    fun SemanticUi.withStyle(): SemanticUi = given(styling.backgroundColor.isSet) { inverted.color(styling.backgroundColor) }
}
