package com.thebase.apps.cms.elements

import com.thebase.apps.cms.elements.common.ElementStyle
import com.thebase.apps.cms.elements.common.nl2br
import com.thebase.apps.cms.elements.common.partial
import com.thebase.apps.cms.elements.common.theBaseColors
import de.peekandpoke.de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div

@Mutable
data class ListElement(
    val styling: ElementStyle = ElementStyle.default,
    val headline: String = "",
    val text: String = "",
    val items1: List<Item> = listOf(),
    val items2: List<Item> = listOf(),
    val items3: List<Item> = listOf()
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "list-element"
    }

    data class Item(
        val icon: String = "",
        val iconColor: SemanticColor = SemanticColor.default,
        val text: String = ""
    )

    inner class VmForm(name: String) : MutatorForm<ListElement, ListElementMutator>(mutator(), name) {

        val styling = subForm(
            ElementStyle.Form(target.styling)
        )

        val headline = field(target::headline)

        val text = field(target::text)

        val items1 = list(target::items1, { Item().mutator() }) { element ->
            subForm(
                ItemForm(element.value)
            )
        }

        val items2 = list(target::items2, { Item().mutator() }) { element ->
            subForm(
                ItemForm(element.value)
            )
        }

        val items3 = list(target::items3, { Item().mutator() }) { element ->
            subForm(
                ItemForm(element.value)
            )
        }
    }

    class ItemForm(item: ListElement_ItemMutator) : MutatorForm<Item, ListElement_ItemMutator>(item) {

        val icon = field(target::icon).acceptsNonBlank().trimmed()

        val iconColor = theBaseColors(target::iconColor)

        val text = field(target::text).acceptsNonBlank().trimmed()
    }

    override fun FlowContent.render() {

        val columns = listOf(items1, items2, items3).filter { it.isNotEmpty() }

        val numCols = when (columns.size) {
            0 -> "zero"
            1 -> "one"
            2 -> "two"
            else -> "three"
        }

        div(classes = "list-element") {

            ui.basic.segment.given(styling.backgroundColor.isSet) { inverted.color(styling.backgroundColor) }.then {

                ui.container {

                    if (headline.isNotBlank()) {
                        ui.header H2 { nl2br(headline) }
                    }

                    if (text.isNotBlank()) {
                        ui.text P { nl2br(text) }
                    }

                    ui.with(numCols).column.grid {

                        columns.forEach { column ->
                            ui.column {
                                ui.list {

                                    column.forEach { item ->

                                        ui.item {
                                            // TODO: icon sizes in "icon" helper class
                                            // TODO: color helpers in "icon" helper class
                                            icon.with("huge").with(item.iconColor.toString()).custom(item.icon)

                                            ui.content P {
                                                nl2br(item.text)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun FlowContent.renderItem(item: Item) {
        icon.with("huge").with(item.iconColor.toString()).custom(item.icon)

        nl2br(item.text)
    }

    override suspend fun editVm(vm: ViewModelBuilder, actions: CmsElement.EditActions): View {

        val form = VmForm(vm.path)

        if (form.submit(vm.call)) {
            if (form.isModified) {
                actions.modify(form.result)
            }
        }

        return vm.view {

            formidable(vm.call.i18n, form) {

                ui.attached.segment {

                    a { attributes["name"] = vm.path }

                    ui.header H3 {
                        icon.list()
                        +"List '$headline'"
                    }

                    partial(this, form.styling)

                    ui.divider {}

                    textInput(form.headline, "Headline")

                    textArea(form.text, "Text")

                    val renderItem: FlowContent.(ItemForm) -> Unit = { item ->

                        ui.two.fields {
                            textInput(item.icon, "Icon")
                            selectInput(item.iconColor, "Color")
                        }
                        textArea(item.text, "Text")
                    }

                    ui.header H4 { +"Items in column #1" }

                    listFieldAsGrid(form.items1, renderItem)

                    ui.header H4 { +"Items in column #2" }

                    listFieldAsGrid(form.items2, renderItem)

                    ui.header H4 { +"Items in column #3" }

                    listFieldAsGrid(form.items3, renderItem)
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }

    }
}
