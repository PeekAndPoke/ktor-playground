package com.thebase.apps.cms.elements

import com.thebase._sortme_.karsten.slickOptions
import com.thebase.apps.cms.elements.common.*
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.enum
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.formidable.withOptions
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.modules.cms.RenderCtx
import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.modules.cms.domain.Image
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.DIV
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.img

@Mutable
data class TextImageElement(
    val styling: ElementStyle = ElementStyle.default,
    val layout: Layout = Layout.ImageLeft,
    val headline: String = "",
    val text: String = "",
    val images: List<Image> = listOf()
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "text-image-element"
    }

    override val name: String get() = "Text & Image '$headline'"

    enum class Layout {
        ImageLeft,
        ImageRight,
        ImageTop,
        ImageBottom
    }

    inner class VmForm(name: String) : MutatorForm<TextImageElement, TextImageElementMutator>(mutator(), name) {

        val styling = styling(target.styling)

        val layout = enum(target::layout).withOptions(
            Layout.ImageLeft to "Image Left".untranslated(),
            Layout.ImageRight to "Image Right".untranslated(),
            Layout.ImageTop to "Image Top".untranslated(),
            Layout.ImageBottom to "Image Bottom".untranslated()
        )

        val headline = field(target::headline)
        val text = field(target::text)

        val images = images(target::images)
    }

    override fun FlowContent.render(ctx: RenderCtx) {

        div(classes = "text-image-element") {

            ui.basic.segment.given(styling.backgroundColor.isSet) { inverted.color(styling.backgroundColor) }.then {

                ui.container {
                    when (layout) {
                        Layout.ImageRight -> ui.two.column.grid {
                            ui.column { textH3(ctx) }
                            ui.column { images() }
                        }

                        Layout.ImageLeft -> ui.two.column.grid {
                            ui.column { images() }
                            ui.column { textH3(ctx) }
                        }

                        Layout.ImageTop -> {
                            images()
                            textH2(ctx)
                        }

                        Layout.ImageBottom -> {
                            textH2(ctx)
                            images()
                        }
                    }
                }
            }
        }
    }

    private fun DIV.textH2(ctx: RenderCtx) {
        ui.header H2 { nl2br(headline) }
        ui.color(styling.textColor).text { ctx.apply { markdown(text) } }
    }

    private fun DIV.textH3(ctx: RenderCtx) {
        ui.header H3 { nl2br(headline) }
        ui.color(styling.textColor).text { ctx.apply { markdown(text) } }
    }

    private fun DIV.images() {

        when {
            images.isEmpty() -> {
                // noop
            }

            images.size == 1 -> img(src = images[0].url, alt = images[0].alt)

            else -> {
                div {
                    slickOptions(
                        slidesToShow = 1,
                        dots = true,
                        infinite = true
                    )

                    images.forEach {
                        img(src = it.url, alt = it.alt)
                    }
                }
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

                    ui.header
                        .given(form.isSubmitted() && form.isNotValid()) { red } H3 {
                        icon.id_card_outline()
                        +"Text And Image '$headline'"
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

                    ui.header H4 { +"Images" }

                    partial(this, form.images)
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
