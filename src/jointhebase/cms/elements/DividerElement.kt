package de.peekandpoke.jointhebase.cms.elements

import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.div

data class DividerElement(
    val background: SemanticColor = SemanticColor.none,
    val height: Height = Height.one
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "divider-element"
    }

    @Suppress("EnumEntryName")
    enum class Height {
        one,
        two,
        three,
        four
    }

    override fun FlowContent.render() {

        div(classes = "divider-element") {

            ui.basic.segment.given(background != SemanticColor.none) { inverted.with(background.toString()) }.then {
                ui.with(height.toString()) {
                    // noop
                }
            }
        }
    }
}
