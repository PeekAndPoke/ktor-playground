package de.peekandpoke.module.cms.elements

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.enum
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.formidable.withOptions
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

    inner class VmForm(name: String) : MutatorForm<GalleryElement, GalleryElementMutator>(mutator(), name) {

        val background = theBaseColors(target::background)

        val layout = enum(target::layout).withOptions(
            Layout.SideBySideSlider to "Side by side Slider".untranslated(),
            Layout.FullWidthSlider to "Full Width Slider".untranslated(),
            Layout.ThreeColumns to "Three Columns Gallery".untranslated(),
            Layout.FiveColumns to "Five Columns Gallery".untranslated()
        )

        val headline = field(target::headline)
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
                    icon.images()
                    +"Gallery '$headline'"
                }

                formidable(vm.call.i18n, form) {

                    selectInput(form.background, "Background-Color")

                    selectInput(form.layout, "Layout")

                    textInput(form.headline, "Headline")

                    submitButton("Submit")
                }
            }
        }
    }
}
