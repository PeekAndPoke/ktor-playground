package com.thebase.apps.cms.elements

import com.thebase._sortme_.karsten.slickOptions
import com.thebase.apps.cms.elements.common.ElementStyle
import com.thebase.apps.cms.elements.common.nl2br
import com.thebase.apps.cms.elements.common.partial
import com.thebase.apps.cms.elements.common.styling
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.modules.cms.RenderCtx
import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.modules.cms.domain.Image
import de.peekandpoke.modules.cms.domain.ImageForm
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

    override val elementName: String get() = "Gallery '$headline' - '$layout'"

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

        val styling = styling(target.styling)

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

    override fun FlowContent.render(ctx: RenderCtx) {

        div {
            classes = setOf("gallery-element", layout.toString())

            ui.basic.segment.given(styling.backgroundColor.isSet) { inverted.color(styling.backgroundColor) }.then {

                texts(ctx)

                when (layout) {
                    Layout.SideBySideSlider -> sideBySide(ctx)

                    Layout.FullWidthSlider -> fullWidth(ctx)

                    Layout.ThreeColumns -> threeColumns(ctx)

                    Layout.FiveColumns -> fiveColumns(ctx)
                }
            }
        }
    }

    private fun DIV.texts(ctx: RenderCtx) {

        if (headline.isNotBlank() || text.isNotBlank()) {

            ui.container {
                if (headline.isNotBlank()) {
                    ui.color(styling.textColor).header H3 { nl2br(headline) }
                }

                if (text.isNotBlank()) {
                    ui.color(styling.textColor).text { ctx.apply { markdown(text) } }
                }
            }
        }
    }

    private fun DIV.sideBySide(ctx: RenderCtx) {
        div {
            slickOptions(
                slidesToShow = 5,
                slidesToScroll = 1,
                dots = false,
                autoplay = true,
                infinite = true
            )

            images(ctx)
        }
    }

    private fun DIV.fullWidth(ctx: RenderCtx) {
        div {
            slickOptions(
                slidesToScroll = 1,
                dots = true,
                infinite = true
            )

            images(ctx)
        }
    }

    private fun DIV.threeColumns(ctx: RenderCtx) {

        ui.three.column.grid {

            items.forEach {
                ui.center.aligned.column.item {
                    image(it)
                    headline(it)
                    text(ctx, it)
                }
            }
        }
    }

    private fun DIV.fiveColumns(ctx: RenderCtx) {

        ui.five.column.grid {

            items.forEach {
                ui.center.aligned.column.item {
                    image(it)
                    headline(it)
                    text(ctx, it)
                }
            }
        }
    }

    private fun DIV.images(ctx: RenderCtx) {

        items.forEach {
            ui.item {
                image(it)
                headline(it)
                text(ctx, it)
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
            ui.color(styling.textColor).header H5 {
                nl2br(it.headline)
            }
        }
    }

    private fun DIV.text(ctx: RenderCtx, it: Item) {
        if (it.text.isNotBlank()) {
            ui.color(styling.textColor).tiny.text {
                ctx.apply { markdown(it.text) }
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

            formidable(vm.call.i18n, form, { action = "#element.${actions.index}" }) {

                ui.attached.segment {

                    ui.header.given(form.isSubmitted() && form.isNotValid()) { red } H3 {
                        icon.images()
                        +"Gallery '$headline'"
                    }

                    ui.three.fields {
                        partial(this, form.styling)
                        selectInput(form.layout, "Layout")
                    }

                    ui.divider {}

                    ui.two.fields {
                        textArea(form.headline, "Headline")
                        textArea(form.text, "Text", "markdown-editor")
                    }

                    ui.header H4 { +"Gallery Items" }

                    listFieldAsGrid(form.items) { item ->

                        textInput(item.headline, "Headline")
                        textArea(item.text, "Text", "markdown-editor")

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
