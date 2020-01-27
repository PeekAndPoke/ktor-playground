package com.thebase.apps.cms.elements

import com.thebase.apps.cms.elements.common.theBaseColors
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
import de.peekandpoke.modules.cms.RenderCtx
import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div

@Mutable
data class DividerElement(
    val background: SemanticColor = SemanticColor.default,
    val height: Height = Height.one
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "divider-element"
    }

    override val name: String get() = "Divider '$height'"

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

    override fun FlowContent.render(ctx: RenderCtx) {

        div(classes = "divider-element") {

            ui.basic.segment.given(background.isSet) { inverted.color(background) }.then {
                ui.with(height.toString()) {
                    // noop
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
                        icon.arrows_alternate_vertical()
                        +"Divider"
                    }

                    ui.two.fields {
                        selectInput(form.background, "Background-Color")
                        selectInput(form.height, "Height")
                    }
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
