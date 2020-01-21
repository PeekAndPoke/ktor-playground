package de.peekandpoke.module.cms.elements

import de.peekandpoke._sortme_.karsten.slickOptions
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.list
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
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.img
import kotlinx.html.style

@Mutable
data class HeroElement(
    val background: SemanticColor = SemanticColor.none,
    val headline: String = "",
    val text: String = "",
    val images: List<Image> = listOf()
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "hero-element"
    }

    inner class VmForm(name: String) : MutatorForm<HeroElement, HeroElementMutator>(mutator(), name) {

        val background = theBaseColors(target::background)

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

            ui.basic.inverted.segment.color(background) {

                ui.container {
                    ui.grid {

                        ui.nine.wide.column {
                            ui.red.header H1 { +headline }
                            ui.red.text P { +text }
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

                    ui.header H3 {
                        icon.html5()
                        +"Hero"
                    }

                    selectInput(form.background, "Background-Color")

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
