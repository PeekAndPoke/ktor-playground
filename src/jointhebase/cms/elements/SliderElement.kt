package de.peekandpoke.jointhebase.cms.elements

import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.img
import kotlinx.html.p

data class SliderElement(
    val background: SemanticColor,
    val items: List<Item>
) : CmsElement {

    data class Item(
        val headline: String,
        val text: String
    )

    override fun FlowContent.render() {

        div(classes = "slider-element") {

            ui.basic.segment.given(background != SemanticColor.none) { inverted.with(background.toString()) }.then {

                ui.horizontal.list {

                    items.forEach {
                        ui.item {
                            // TODO: image url
                            img(src = "https://picsum.photos/300") {}

                            ui.header H4 {
                                +it.headline
                            }

                            p {
                                +it.text
                            }
                        }
                    }
                }
            }
        }
    }
}
