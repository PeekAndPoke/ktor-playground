package de.peekandpoke.jointhebase.cms.elements

import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.*

data class GalleryElement(
    val background: SemanticColor = SemanticColor.none,
    val layout: Layout = Layout.SideBySideSlider,
    val headline: String = "",
    val items: List<Item> = listOf()
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "gallery-element"
    }

    data class Item(
        val headline: String = "",
        val text: String = "",
        val image: String = ""  // TODO: use better type
    )

    enum class Layout {
        SideBySideSlider,
        FullWidthSlider,
        ThreeColumns,
        FiveColumns
    }

    override fun FlowContent.render() {

        div {
            classes = setOf("gallery-element", layout.toString())

            ui.basic.segment.given(background != SemanticColor.none) { inverted.with(background.toString()) }.then {

                if (headline.isNotBlank()) {
                    ui.header H2 {
                        +headline
                    }
                }

                when (layout) {
                    Layout.SideBySideSlider -> sideBySide()

                    Layout.FullWidthSlider -> fullWidth()

                    Layout.ThreeColumns -> threeColumns()

                    Layout.FiveColumns -> fiveColumns()
                }
            }
        }
    }

    private fun DIV.sideBySide() {
        div {
            // TODO: helper class for Slick data
            attributes["data-slick"] = "{\"slidesToShow\": 5, \"slidesToScroll\": 1, \"dots\": true, \"autoplay\": true, \"infinite\": true}"

            images()
        }
    }

    private fun DIV.fullWidth() {
        div {
            // TODO: helper class for Slick data
            attributes["data-slick"] = "{\"slidesToShow\": 1, \"dots\": true, \"infinite\": true}"

            images()
        }
    }

    private fun DIV.threeColumns() {

        ui.three.column.grid {

            items.forEach {
                ui.column.item {
                    image(it)
                    headline(it)
                    text(it)
                }
            }
        }
    }

    private fun DIV.fiveColumns() {

        ui.five.column.grid {

            items.forEach {
                ui.column.item {
                    image(it)
                    headline(it)
                    text(it)
                }
            }
        }
    }

    private fun DIV.images() {

        items.forEach {
            ui.item {
                image(it)
                headline(it)
                text(it)
            }
        }
    }

    private fun DIV.image(it: Item) {
        if (it.image.isNotBlank()) {
            img(src = it.image) {}
        }
    }

    private fun DIV.headline(it: Item) {
        if (it.headline.isNotBlank()) {
            ui.header H4 {
                +it.headline
            }
        }
    }

    private fun DIV.text(it: Item) {
        if (it.text.isNotBlank()) {
            p {
                +it.text
            }
        }
    }
}
