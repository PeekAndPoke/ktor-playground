package de.peekandpoke.jointhebase.cms.elements

import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.p

data class ListElement(
    val background: SemanticColor = SemanticColor.none,
    val layout: Layout = Layout.TwoColumns,
    val headline: String = "",
    val text: String = "",
    val items: List<Item> = listOf()
) : CmsElement {

    data class Item(
        val icon: String = "", // TODO: use a better data-type
        val text: String = ""
    )

    enum class Layout {
        TwoColumns
    }

    override fun FlowContent.render() {

        div(classes = "list-element") {

            ui.basic.segment.given(background != SemanticColor.none) { inverted.with(background.toString()) }.then {

                if (headline.isNotBlank()) {
                    ui.header H2 { +headline }
                }

                if (text.isNotBlank()) {
                    p { +text }
                }

                when (layout) {
                    Layout.TwoColumns -> {

                        ui.two.column.grid {

                            items.forEach {

                                ui.column {
                                    icon.custom(it.icon)
                                    +it.text
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
