package de.peekandpoke.module.cms.elements

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
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

@Mutable
data class FooterElement(
    val background: SemanticColor = SemanticColor.none
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "footer-element"
    }

    inner class VmForm(name: String) : MutatorForm<FooterElement, FooterElementMutator>(mutator(), name) {

        val background = theBaseColors(target::background)
    }


    override fun FlowContent.render() {

        ui.basic.inverted.segment.with(background.toString()) {

            ui.three.column.grid {

                ui.row {

                    ui.column {

                        div {
                            +"See you soon"
                        }
                    }

                    ui.column {
                        +"Contact"
                    }

                    ui.column {
                        +"Let's meet up no matter where"
                    }
                }

                ui.row {
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

                    ui.column {
                        +"The Base"
                    }

                    ui.column {
                        icon.copyright_outline()
                        +"2020 The Base - Future Of Living"
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

                    ui.header H3 { +"Footer" }

                    selectInput(form.background, "Background-Color")
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
