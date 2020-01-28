package com.thebase.apps.cms.elements

import com.thebase.apps.cms.elements.common.ElementStyle
import com.thebase.apps.cms.elements.common.nl2br
import com.thebase.apps.cms.elements.common.partial
import com.thebase.apps.cms.elements.common.styling
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
import kotlinx.html.div

@Mutable
data class FooterElement(
    val styling: ElementStyle = ElementStyle.default,
    val headline: String = "",
    val middle: String = "",
    val right: String = ""
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "footer-element"
    }

    override val elementName: String get() = "Footer '$headline'"

    inner class VmForm(name: String) : MutatorForm<FooterElement, FooterElementMutator>(mutator(), name) {

        val styling = styling(target.styling)

        val headline = field(target::headline)
        val middle = field(target::middle)
        val right = field(target::right)
    }

    override fun FlowContent.render(ctx: RenderCtx) {

        div(classes = "footer-element") {
            ui.basic.segment.given(styling.backgroundColor.isSet) { inverted.color(styling.backgroundColor) }.then {

                ui.container {
                    ui.three.column.grid.color(styling.textColor).text {

                        ui.row {
                            ui.six.wide.column {
                                div(classes = "see-you") {
                                    nl2br(headline)
                                }
                            }

                            ui.five.wide.column {
                                ui.tiny.text {
                                    ctx.apply { markdown(middle) }
                                }
                            }

                            ui.five.wide.right.aligned.column {
                                ui.header H5 { nl2br(right) }
                            }
                        }

                        ui.row.with("last-row") {
                            ui.column {
                                ui.horizontal.list {
                                    ui.item {
                                        +"Legal Notice"
                                    }
                                    ui.item {
                                        +"Data Privacy"
                                    }
                                    ui.item {
                                        +"Disclaimer"
                                    }
                                }
                            }

                            ui.center.aligned.column {
                                ui.header.with("the-base-logo") {
                                    +"THE BASE"
                                }
                            }

                            ui.right.aligned.column {
                                icon.copyright_outline()
                                +"2020 The Base - Future Of Living"
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

            formidable(vm.call.i18n, form, { action = "#element.${actions.index}" }) {

                ui.attached.segment {

                    ui.header.given(form.isSubmitted() && form.isNotValid()) { red } H3 {
                        icon.arrow_circle_down()
                        +"Footer"
                    }

                    ui.two.fields {
                        partial(this, form.styling)
                    }

                    ui.divider {}

                    ui.three.fields {
                        textArea(form.headline, "Headline")
                        textArea(form.middle, "Middle", "markdown-editor")
                        textArea(form.right, "Right")
                    }
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
