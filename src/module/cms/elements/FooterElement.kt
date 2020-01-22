package de.peekandpoke.module.cms.elements

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.module.cms.CmsElement
import de.peekandpoke.module.cms.elements.common.ElementStyle
import de.peekandpoke.module.cms.elements.common.nl2br
import de.peekandpoke.module.cms.elements.common.partial
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div

@Mutable
data class FooterElement(
    val styling: ElementStyle = ElementStyle.default,
    val headline: String = ""
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "footer-element"
    }

    inner class VmForm(name: String) : MutatorForm<FooterElement, FooterElementMutator>(mutator(), name) {

        val styling = subForm(
            ElementStyle.Form(target.styling)
        )

        val headline = field(target::headline)
    }

    override fun FlowContent.render() {

        ui.basic.segment
            .with("footer-element")
            .given(styling.backgroundColor.isSet) { inverted.color(styling.backgroundColor) }.then {

                ui.container {
                    ui.three.column.grid.color(styling.textColor).text {

                        ui.row {
                            ui.six.wide.column {
                                div(classes = "see-you") {
                                    nl2br(headline)
                                }
                            }

                            ui.five.wide.column {
                                div(classes = "contact") {
                                    ui.header H3 {
                                        +"Contact"
                                    }
                                    ui.text P {
                                        a(href = "mailto:hello@jointhebase.co") {
                                            +"hello@jointhebase.co"
                                        }
                                    }
                                }
                            }

                            ui.five.wide.right.aligned.column {
                                ui.header H3 {
                                    +"Let's meet up no matter where"
                                }
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
                        icon.arrow_circle_down()
                        +"Footer"
                    }

                    partial(this, form.styling)

                    ui.divider {}

                    textArea(form.headline, "Headline")
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
