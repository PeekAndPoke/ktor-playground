package de.peekandpoke.module.cms.elements

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.enum
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.formidable.withOptions
import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.module.cms.CmsElement
import de.peekandpoke.module.cms.forms.theBaseColors
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div

@Mutable
data class DividerElement(
    val background: SemanticColor = SemanticColor.none,
    val height: Height = Height.one
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "divider-element"
    }

    @Suppress("EnumEntryName")
    enum class Height {
        one,
        two,
        three,
        four
    }

    inner class VmForm(name: String) : MutatorForm<DividerElement, DividerElementMutator>(mutator(), name) {

        val background = theBaseColors(target::background)

        val height = enum(target::height).withOptions(
            Height.one to "one".untranslated(),
            Height.two to "two".untranslated(),
            Height.three to "three".untranslated(),
            Height.four to "four".untranslated()
        )
    }

    override fun FlowContent.render() {

        div(classes = "divider-element") {

            ui.basic.segment.given(background != SemanticColor.none) { inverted.with(background.toString()) }.then {
                ui.with(height.toString()) {
                    // noop
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
                        icon.arrows_alternate_vertical()
                        +"Divider"
                    }

                    selectInput(form.background, "Background-Color")

                    textInput(form.height, "Height")
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
