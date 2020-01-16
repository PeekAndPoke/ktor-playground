package de.peekandpoke.module.cms.elements

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.module.cms.CmsElement
import de.peekandpoke.module.cms.forms.theBaseColors
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.*

@Mutable
data class ListElement(
    val background: SemanticColor = SemanticColor.none,
    val layout: Layout = Layout.TwoColumns,
    val headline: String = "",
    val text: String = "",
    val items: List<Item> = listOf()
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "list-element"
    }

    data class Item(
        val icon: String = "", // TODO: use a better data-type
        val text: String = ""
    )

    enum class Layout {
        TwoColumns,
        ThreeColumns
    }

    inner class VmForm(name: String) : MutatorForm<ListElement, ListElementMutator>(mutator(), name) {

        val background = theBaseColors(target::background)

        val layout = enum(target::layout).withOptions(
            Layout.TwoColumns to "Two Columns".untranslated(),
            Layout.ThreeColumns to "Three Columns".untranslated()
        )

        val headline = field(target::headline)

        val text = field(target::text)

        val items = list(target::items, { Item().mutator() }) { element ->
            subForm(
                ItemForm(element.value)
            )
        }
    }

    class ItemForm(item: ListElement_ItemMutator) : MutatorForm<Item, ListElement_ItemMutator>(item) {

        val icon = field(target::icon).acceptsNonBlank().trimmed()

        val text = field(target::text).acceptsNonBlank().trimmed()
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
                    Layout.TwoColumns ->
                        ui.two.column.grid {
                            items.forEach {
                                ui.column {
                                    icon.custom(it.icon)
                                    +it.text
                                }
                            }
                        }

                    Layout.ThreeColumns ->
                        ui.three.column.grid {
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

    override suspend fun editVm(vm: ViewModelBuilder, onChange: (CmsElement) -> Unit): View {

        val form = VmForm(vm.path)

        if (form.submit(vm.call)) {
            if (form.isModified) {
                onChange(form.result)
            }
        }

        return vm.view {
            ui.segment {

                a {
                    attributes["name"] = vm.path
                }

                ui.header H3 {
                    icon.list()
                    +"List '$headline'"
                }

                formidable(vm.call.i18n, form) {

                    selectInput(form.background, "Background-Color")

                    selectInput(form.layout, "Layout")

                    textInput(form.headline, "Headline")

                    textArea(form.text, "Text")

                    ui.basic.segment {

                        ui.header H4 { +"Items" }

                        val renderItem = { item: ItemForm ->
                            ui.column {
                                listFieldItem()

                                ui.attached.segment {
                                    textInput(item.icon, "Icon")
                                    textArea(item.text, "Text")
                                }

                                ui.bottom.attached.buttons {
                                    ui.icon.button {
                                        listFieldRemoveAction()
                                        title = "Remove Item"
                                        icon.close()
                                    }
                                }
                            }
                        }

                        ui.three.column.grid {
                            listFieldContainer(form.items) { dummy -> renderItem(dummy) }

                            form.items.forEach(renderItem)

                            ui.column {
                                listFieldAddAction()

                                ui.placeholder.raised.segment {
                                    ui.icon.header {
                                        icon.plus()
                                    }
                                }
                            }
                        }
                    }

                    ui.attached.segment {
                        submitButton("Submit")
                    }
                }
            }
        }

    }
}
