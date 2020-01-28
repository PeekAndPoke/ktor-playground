package com.thebase.apps.cms.elements

import com.thebase.apps.cms.elements.common.theBaseColors
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.enum
import de.peekandpoke.ktorfx.formidable.field
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
import kotlinx.html.classes
import kotlinx.html.div

@Mutable
data class DividerElement(
    val background: SemanticColor = SemanticColor.default,
    val height: Height = Height.one,
    val pattern: Int = 1
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "divider-element"
    }

    override val elementName: String get() = "Divider '$height'"

    @Suppress("EnumEntryName")
    enum class Height {
        one,
        two,
        three,
        four,
        five,
        six
    }

    inner class VmForm(name: String) : MutatorForm<DividerElement, DividerElementMutator>(mutator(), name) {

        val background = theBaseColors(target::background)

        val height = enum(target::height).withOptions(
            Height.one to "one".untranslated(),
            Height.two to "two".untranslated(),
            Height.three to "three".untranslated(),
            Height.four to "four".untranslated(),
            Height.five to "five".untranslated(),
            Height.six to "six".untranslated()
        )

        val pattern = field(target::pattern).withOptions(
            0 to "No Pattern".untranslated(),
            1 to "Pattern #1".untranslated(),
            2 to "Pattern #2".untranslated(),
            3 to "Pattern #3".untranslated(),
            4 to "Pattern #4".untranslated(),
            5 to "Pattern #5".untranslated(),
            6 to "Pattern #6".untranslated()
        )
    }

    override fun FlowContent.render(ctx: RenderCtx) {

        div(classes = "divider-element") {

            ui.basic.segment.given(background.isSet) { inverted.color(background) }.then {
                ui.with(height.toString()) {

                    if (pattern != 0) {
                        div {
                            classes = setOf("pattern", "pattern-${pattern.toString().padStart(3, '0')}")
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
                        icon.arrows_alternate_vertical()
                        +"Divider"
                    }

                    ui.three.fields {
                        selectInput(form.background, "Background-Color")
                        selectInput(form.height, "Height")
                        selectInput(form.pattern, "Pattern")
                    }
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
