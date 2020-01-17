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
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.p

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
        val icon: String = "",
        val iconColor: SemanticColor = SemanticColor.none,
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

        val iconColor = theBaseColors(target::iconColor)

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
                                ui.column { renderItem(it) }
                            }
                        }

                    Layout.ThreeColumns ->
                        ui.three.column.grid {
                            items.forEach {
                                ui.column { renderItem(it) }
                            }
                        }
                }
            }
        }
    }

    private fun FlowContent.renderItem(item: Item) {
        icon.with(item.iconColor.toString()).custom(item.icon)
        +item.text
    }

    override suspend fun editVm(vm: ViewModelBuilder, onChange: (CmsElement) -> Unit): View {

        val form = VmForm(vm.path)

        if (form.submit(vm.call)) {
            if (form.isModified) {
                onChange(form.result)
            }
        }

        return vm.view {

            formidable(vm.call.i18n, form) {

                ui.top.attached.blue.segment {

                    a { attributes["name"] = vm.path }

                    ui.header H3 {
                        icon.list()
                        +"List '$headline'"
                    }

                    selectInput(form.background, "Background-Color")

                    selectInput(form.layout, "Layout")

                    textInput(form.headline, "Headline")

                    textArea(form.text, "Text")

                    ui.header H4 { +"Items" }

                    listFieldAsGrid(form.items) { item ->
                        ui.two.fields {
                            textInput(item.icon, "Icon")
                            selectInput(item.iconColor, "Color")
                        }
                        textArea(item.text, "Text")
                    }
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }

    }
}
