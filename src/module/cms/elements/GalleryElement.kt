package de.peekandpoke.module.cms.elements

import de.peekandpoke._sortme_.karsten.slickOptions
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.module.cms.CmsElement
import de.peekandpoke.module.cms.domain.Image
import de.peekandpoke.module.cms.elements.common.ElementStyle
import de.peekandpoke.module.cms.elements.common.nl2br
import de.peekandpoke.module.cms.elements.common.partial
import de.peekandpoke.module.cms.forms.ImageForm
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.*

@Mutable
data class GalleryElement(
    val styling: ElementStyle = ElementStyle.default,
    val layout: Layout = Layout.SideBySideSlider,
    val headline: String = "",
    val text: String = "",
    val items: List<Item> = listOf()
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "gallery-element"
    }

    data class Item(
        val headline: String = "",
        val text: String = "",
        val image: Image = Image()
    )

    enum class Layout {
        SideBySideSlider,
        FullWidthSlider,
        ThreeColumns,
        FiveColumns
    }

    inner class VmForm(name: String) : MutatorForm<GalleryElement, GalleryElementMutator>(mutator(), name) {

        val styling = subForm(
            ElementStyle.Form(target.styling)
        )

        val layout = enum(target::layout).withOptions(
            Layout.SideBySideSlider to "Side by side Slider".untranslated(),
            Layout.FullWidthSlider to "Full Width Slider".untranslated(),
            Layout.ThreeColumns to "Three Columns Gallery".untranslated(),
            Layout.FiveColumns to "Five Columns Gallery".untranslated()
        )

        val headline = field(target::headline)

        val text = field(target::text)

        val items = list(target::items, { Item().mutator() }) { item ->
            subForm(
                ItemForm(item.value)
            )
        }
    }

    class ItemForm(item: GalleryElement_ItemMutator) : MutatorForm<Item, GalleryElement_ItemMutator>(item) {

        val headline = field(target::headline)

        val text = field(target::text)

        val image = subForm(
            ImageForm(target.image)
        )
    }

    override fun FlowContent.render() {

        div {
            classes = setOf("gallery-element", layout.toString())

            ui.basic.segment.given(styling.backgroundColor.isSet) { inverted.color(styling.backgroundColor) }.then {

                texts()

                when (layout) {
                    Layout.SideBySideSlider -> sideBySide()

                    Layout.FullWidthSlider -> fullWidth()

                    Layout.ThreeColumns -> threeColumns()

                    Layout.FiveColumns -> fiveColumns()
                }
            }
        }
    }

    private fun DIV.texts() {

        if (headline.isNotBlank() || text.isNotBlank()) {

            ui.container {
                if (headline.isNotBlank()) {
                    ui.color(styling.textColor).header H2 {
                        nl2br(headline)
                    }
                }

                if (text.isNotBlank()) {
                    ui.color(styling.textColor).text P {
                        nl2br(text)
                    }
                }
            }
        }
    }

    private fun DIV.sideBySide() {
        div {
            slickOptions(
                slidesToShow = 5,
                slidesToScroll = 1,
                dots = false,
                autoplay = true,
                infinite = true
            )

            images()
        }
    }

    private fun DIV.fullWidth() {
        div {
            slickOptions(
                slidesToScroll = 1,
                dots = true,
                infinite = true
            )

            images()
        }
    }

    private fun DIV.threeColumns() {

        ui.three.column.grid {

            items.forEach {
                ui.center.aligned.column.item {
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
                ui.center.aligned.column.item {
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
        if (it.image.url.isNotBlank()) {
            img(src = it.image.url, alt = it.image.alt) {}
        }
    }

    private fun DIV.headline(it: Item) {
        if (it.headline.isNotBlank()) {
            ui.color(styling.textColor).header H3 {
                nl2br(it.headline)
            }
        }
    }

    private fun DIV.text(it: Item) {
        if (it.text.isNotBlank()) {
            ui.color(styling.textColor).text P {
                nl2br(it.text)
            }
        }
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
                        icon.images()
                        +"Gallery '$headline'"
                    }

                    partial(this, form.styling)

                    ui.divider {}

                    selectInput(form.layout, "Layout")

                    textInput(form.headline, "Headline")

                    textArea(form.text, "Text")

                    ui.header H4 { +"Gallery Items" }

                    listFieldAsGrid(form.items) { item ->

                        textInput(item.headline, "Headline")
                        textArea(item.text, "Text")

                        textInput(item.image.url, "Image Url")
                        textInput(item.image.alt, "Image Alt")

                        img(src = item.image.url.textValue, alt = item.image.alt.textValue) {
                            style = "max-width: 100%; max-height: 200px;"
                        }
                    }
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
