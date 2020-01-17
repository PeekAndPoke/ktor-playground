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
import de.peekandpoke.module.cms.domain.Image
import de.peekandpoke.module.cms.domain.mutator
import de.peekandpoke.module.cms.forms.ImageForm
import de.peekandpoke.module.cms.forms.theBaseColors
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.*

@Mutable
data class TextImageElement(
    val background: SemanticColor = SemanticColor.none,
    val layout: Layout = Layout.ImageLeft,
    val headline: String = "",
    val text: String = "",
    val images: List<Image> = listOf()
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "text-image-element"
    }

    enum class Layout {
        ImageLeft,
        ImageRight,
        ImageTop,
        ImageBottom
    }

    inner class VmForm(name: String) : MutatorForm<TextImageElement, TextImageElementMutator>(mutator(), name) {

        val background = theBaseColors(target::background)

        val layout = enum(target::layout).withOptions(
            Layout.ImageLeft to "Image Left".untranslated(),
            Layout.ImageRight to "Image Right".untranslated(),
            Layout.ImageTop to "Image Top".untranslated(),
            Layout.ImageBottom to "Image Bottom".untranslated()
        )

        val headline = field(target::headline)

        val text = field(target::text)

        val images = list(target::images, { Image().mutator() }) { item ->
            subForm(
                ImageForm(item.value)
            )
        }
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

            images.size == 1 -> img(src = images[0].url, alt = images[0].alt)

            else -> {
                div {
                    attributes["data-slick"] = "{\"slidesToShow\": 1, \"dots\": true, \"infinite\": true}"

                    images.forEach {
                        img(src = it.url, alt = it.alt)
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

            formidable(vm.call.i18n, form) {

                ui.top.attached.blue.segment {

                    a { attributes["name"] = vm.path }

                    ui.header H3 {
                        icon.id_card_outline()
                        +"Text And Image '$headline'"
                    }

                    ui.two.fields {
                        selectInput(form.background, "Background-Color")
                        selectInput(form.layout, "Layout")
                    }

                    textInput(form.headline, "Headline")

                    textArea(form.text, "Text")

                    ui.header H4 { +"Images" }

                    listFieldAsGrid(form.images) { item ->

                        textInput(item.url, "Url")
                        textInput(item.alt, "Alt Text")

                        img(src = item.url.textValue, alt = item.alt.textValue) {
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
