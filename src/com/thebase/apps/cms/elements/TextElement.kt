package com.thebase.apps.cms.elements

import com.thebase.apps.cms.elements.common.ElementStyle
import com.thebase.apps.cms.elements.common.nl2br
import com.thebase.apps.cms.elements.common.partial
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.modules.cms.RenderCtx
import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div

@Mutable
data class TextElement(
    val styling: ElementStyle = ElementStyle.default,
    val headline: String = "",
    val text: String = ""
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "text-element"
    }

    override val name: String get() = "Text '$headline'"

    inner class VmForm(name: String) : MutatorForm<TextElement, TextElementMutator>(mutator(), name) {

        val styling = subForm(
            ElementStyle.Form(target.styling)
        )

        val headline = field(target::headline)

        val text = field(target::text)
    }

    override fun FlowContent.render(ctx: RenderCtx) {

        div(classes = "text-element") {

            ui.basic.segment.given(styling.backgroundColor.isSet) { inverted.color(styling.backgroundColor) }.then {

                ui.container {

                    if (headline.isNotBlank()) {
                        ui.color(styling.textColor).header H2 {
                            nl2br(headline)
                        }
                    }

                    if (text.isNotBlank()) {
                        ui.color(styling.textColor).text {
                            ctx.apply { markdown(text) }
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

                    a { attributes["name"] = vm.path }

                    ui.header H3 {
                        icon.quote_right()
                        +"Text '$headline'"
                    }

                    ui.two.fields {
                        partial(this, form.styling)
                    }

                    ui.divider {}

                    ui.two.fields {
                        textArea(form.headline, "Headline")
                        textArea(form.text, "Text", "markdown-editor")
                    }
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
