package de.peekandpoke.jointhebase.cms.elements

import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import kotlinx.html.FlowContent
import kotlinx.html.div

data class HeadlineElement(
    val background: SemanticColor,
    val text: String
) : CmsElement {

    override fun FlowContent.render() {

        div(classes = "headline-element") {

            ui.basic.segment.given(background != SemanticColor.none) { inverted.with(background.toString()) }.then {
                ui.header H1 {
                    +text
                }
            }
        }

    }
}
