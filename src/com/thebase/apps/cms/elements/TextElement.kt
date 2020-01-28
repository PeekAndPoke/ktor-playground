package com.thebase.apps.cms.elements

import com.thebase.apps.cms.elements.common.*
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.formidable.trimmed
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.modules.cms.RenderCtx
import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.div

@Mutable
data class TextElement(
    override val styling: ElementStyle = ElementStyle.default,
    override val padding: ElementPadding = ElementPadding.default,
    val headline: String = "",
    val text: String = ""
) : CmsElement, StyledElement, PaddedElement {

    companion object : Polymorphic.Child {
        override val identifier = "text-element"
    }

    override val elementName: String get() = "Text '$headline'"

    inner class VmForm(name: String) : MutatorForm<TextElement, TextElementMutator>(mutator(), name) {

        val styling = styling(target.styling)
        val padding = padding(target.padding)

        val headline = field(target::headline).trimmed()

        val text = field(target::text).trimmed()
    }

    override fun FlowContent.render(ctx: RenderCtx) {

        div(classes = "text-element") {

            ui.basic.withStyle().withPadding().segment {

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
                        icon.quote_right()
                        +"Text '$headline'"
                    }

                    ui.two.fields {
                        partial(this, form.styling)
                        partial(this, form.padding)
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
