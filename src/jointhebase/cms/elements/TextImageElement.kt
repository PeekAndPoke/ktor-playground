package de.peekandpoke.jointhebase.cms.elements

import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import kotlinx.html.DIV
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.img

data class TextImageElement(
    val background: SemanticColor = SemanticColor.none,
    val layout: Layout = Layout.ImageLeft,
    val headline: String = "",
    val text: String = "",
    val images: List<String> = listOf()
) : CmsElement {

    enum class Layout {
        ImageLeft,
        ImageRight,
        ImageTop,
        ImageBottom
    }

    override fun FlowContent.render() {

        div(classes = "text-image-element") {

            ui.basic.segment.given(background != SemanticColor.none) { inverted.with(background.toString()) }.then {

                when (layout) {
                    Layout.ImageRight -> ui.two.column.grid {
                        ui.column { text() }
                        ui.column { image() }
                    }

                    Layout.ImageLeft -> ui.two.column.grid {
                        ui.column { image() }
                        ui.column { text() }
                    }

                    Layout.ImageTop -> {
                        image()
                        text()
                    }

                    Layout.ImageBottom -> {
                        text()
                        image()
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

        when {
            images.isEmpty() -> {
                // noop
            }

            images.size == 1 -> img(src = images[0])

            else -> {
                div {
                    attributes["data-slick"] = "{\"slidesToShow\": 1, \"dots\": true, \"infinite\": true}"

                    images.forEach {
                        img(src = it)
                    }
                }
            }
        }
    }
}
