package de.peekandpoke.jointhebase.cms.elements

import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.img

data class HeroElement(
    val background: SemanticColor = SemanticColor.none,
    val headline: String = "",
    val text: String = "",
    val images: List<String> = listOf()  // TODO: Use more specific type than "String" for image urls
) : CmsElement {

    override fun FlowContent.render() {

        div(classes = "hero-element") {

            ui.basic.inverted.segment.with(background.toString()) {

                ui.two.column.grid {

                    ui.column {
                        ui.red.header H1 { +headline }
                        ui.red.header H3 { +text }
                    }

                    // TODO: image carousel: http://kenwheeler.github.io/slick/

                    ui.column.right.aligned {

                        div {
                            // TODO: helper class for Slick data
                            attributes["data-slick"] = "{\"slidesToShow\": 1, \"dots\": true, \"infinite\": true}"

                            images.forEach {
                                img(src = it) {}
                            }
                        }
                    }
                }
            }
        }
    }
}
