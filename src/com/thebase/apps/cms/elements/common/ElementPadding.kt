package com.thebase.apps.cms.elements.common

import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.withOptions
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated

@Mutable
data class ElementPadding(
    val paddingTop: Boolean = true,
    val paddingBottom: Boolean = true
) {

    companion object {
        val default = ElementPadding()
    }

    class Form(padding: ElementPaddingMutator) : MutatorForm<ElementPadding, ElementPaddingMutator>(padding) {

        val paddingTop = field(target::paddingTop).withOptions(true to "yes".untranslated(), false to "no".untranslated())

        val paddingBottom = field(target::paddingBottom).withOptions(true to "yes".untranslated(), false to "no".untranslated())
    }
}

