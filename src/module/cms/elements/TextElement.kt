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
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.p

@Mutable
data class TextElement(
    val background: SemanticColor = SemanticColor.none,
    val headline: String = "",
    val text: String = ""
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "text-element"
    }

    inner class VmForm(name: String) : MutatorForm<TextElement, TextElementMutator>(mutator(), name) {

        val background = theBaseColors(target::background)

        val headline = field(target::headline)

        val text = field(target::text)
    }

    override fun FlowContent.render() {

        div(classes = "text-element") {

            ui.basic.segment.given(background != SemanticColor.none) { inverted.with(background.toString()) }.then {

                if (headline.isNotBlank()) {
                    ui.header H2 { +headline }
                }

                if (text.isNotBlank()) {
                    p { +text }
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
                        icon.quote_right()
                        +"Text '$headline'"
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
