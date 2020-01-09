package de.peekandpoke.jointhebase.cms.elements

import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import kotlinx.html.DIV
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.img

data class TextImageElement(
    val background: SemanticColor,
    val textLeft: Boolean,
    val headline: String,
    val text: String
) : CmsElement {

    override fun FlowContent.render() {

        div(classes = "text-image-element") {

            ui.basic.segment.given(background != SemanticColor.none) { inverted.with(background.toString()) }.then {

                when (textLeft) {
                    true -> ui.two.column.grid {
                        ui.column { text() }
                        ui.column { image() }
                    }

                    else -> ui.two.column.grid {
                        ui.column { image() }
                        ui.column { text() }
                    }
                }
            }
        }
    }

    private fun DIV.text() {
        ui.header H3 { +headline }
        ui.item P { +text }
    }

    private fun DIV.image() {
        // TODO: image url
        img(src = "https://picsum.photos/600/400")
    }
}
