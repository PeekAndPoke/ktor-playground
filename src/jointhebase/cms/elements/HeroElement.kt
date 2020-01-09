package de.peekandpoke.jointhebase.cms.elements

import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.img

data class HeroElement(
    val background: SemanticColor,
    val headline: String,
    val subHeadline: String
) : CmsElement {

    override fun FlowContent.render() {

        div(classes = "hero-element") {

            ui.basic.inverted.segment.with(background.toString()) {

                ui.two.column.grid {

                    ui.column {
                        ui.red.header H1 { +headline }
                        ui.red.header H2 { +subHeadline }
                    }

                    // TODO: image carousel: http://kenwheeler.github.io/slick/

                    ui.column.right.aligned {
                        img(src = "https://picsum.photos/500/600") {}
                    }
                }
            }
        }
    }
}
