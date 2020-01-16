package de.peekandpoke.module.cms.elements

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.module.cms.CmsElement
import de.peekandpoke.module.cms.forms.theBaseColors
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.img

@Mutable
data class HeroElement(
    val background: SemanticColor = SemanticColor.none,
    val headline: String = "",
    val text: String = "",
    val images: List<String> = listOf()  // TODO: Use more specific type than "String" for image urls
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "hero-element"
    }

    inner class VmForm(name: String) : MutatorForm<HeroElement, HeroElementMutator>(mutator(), name) {

        val background = theBaseColors(target::background)

        val headline = field(target::headline)

        val text = field(target::text)
    }

    override fun FlowContent.render() {

        div(classes = "hero-element") {

            ui.basic.inverted.segment.with(background.toString()) {

                ui.two.column.grid {

                    ui.column {
                        ui.red.header H1 { +headline }
                        ui.red.header H3 { +text }
                    }

                    ui.column.right.aligned {

                        div {
                            // TODO: helper class for Slick data
                            attributes["data-slick"] = "{\"slidesToShow\": 1, \"dots\": true, \"infinite\": true}"

                            images.forEach {
                                img(src = it) {}
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

            formidable(vm.call.i18n, form) {

                ui.top.attached.blue.segment {

                    ui.header H3 {
                        icon.html5()
                        +"Hero"
                    }

                    selectInput(form.background, "Background-Color")

                    textInput(form.headline, "Headline")

                    textArea(form.text, "Text")

                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
