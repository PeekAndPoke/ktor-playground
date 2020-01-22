package com.thebase.apps.cms.elements

import com.thebase._sortme_.karsten.slickOptions
import com.thebase.apps.cms.elements.common.ElementStyle
import com.thebase.apps.cms.elements.common.nl2br
import com.thebase.apps.cms.elements.common.partial
import de.peekandpoke.de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.modules.cms.domain.Image
import de.peekandpoke.modules.cms.domain.ImageForm
import de.peekandpoke.modules.cms.domain.mutator
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.img

@Mutable
data class HeroElement(
    val styling: ElementStyle = ElementStyle.default,
    val layout: Layout = Layout.ImageRight,
    val headline: String = "",
    val text: String = "",
    val images: List<Image> = listOf()
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "hero-element"
    }

    enum class Layout {
        ImageRight,
        FullSizeImage
    }

    inner class VmForm(name: String) : MutatorForm<HeroElement, HeroElementMutator>(mutator(), name) {

        val styling = subForm(
            ElementStyle.Form(target.styling)
        )

        val layout = enum(target::layout).withOptions(
            Layout.ImageRight to "Image on the right".untranslated(),
            Layout.FullSizeImage to "Full size image".untranslated()
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

        div(classes = "hero-element") {

            ui.basic.inverted.segment.color(styling.backgroundColor) {

                when (layout) {
                    Layout.ImageRight -> renderImageRight()

                    Layout.FullSizeImage -> renderFullSizeImage()
                }
            }
        }
    }

    private fun FlowContent.renderImageRight() {

        ui.container {
            ui.grid {

                ui.nine.wide.column {
                    ui.color(styling.textColor).header H1 { nl2br(headline) }
                    ui.color(styling.textColor).text P { nl2br(text) }
                }

                ui.seven.wide.column.right.aligned {

                    div(classes = "image-container") {

                        when (images.size) {
                            0 -> {
                                // noop
                            }

                            1 -> img(src = images[0].url, alt = images[0].alt)

                            else -> {
                                slickOptions(
                                    slidesToShow = 1,
                                    dots = true,
                                    infinite = true,
                                    fade = true,
                                    arrows = false,
                                    autoplay = true
                                )

                                images.forEach {
                                    img(src = it.url, alt = it.alt)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun FlowContent.renderFullSizeImage() {
        +"TODO"
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

                    ui.header H3 {
                        icon.html5()
                        +"Hero"
                    }

                    partial(this, form.styling)

                    selectInput(form.layout, "Layout")

                    ui.divider {}

                    textArea(form.headline, "Headline")

                    textArea(form.text, "Text")

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
