package com.thebase.apps.cms.elements.common

import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ultra.mutator.Mutable

@Mutable
data class ElementStyle(
    val backgroundColor: SemanticColor = SemanticColor.default,
    val textColor: SemanticColor = SemanticColor.default
) {

    companion object {
        val default = ElementStyle()
    }

    class Form(style: ElementStyleMutator) : MutatorForm<ElementStyle, ElementStyleMutator>(style) {

        val textColor = theBaseColors(target::textColor)

        val backgroundColor = theBaseColors(target::backgroundColor)
    }
}

