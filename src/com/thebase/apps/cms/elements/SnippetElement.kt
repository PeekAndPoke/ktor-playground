package com.thebase.apps.cms.elements

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.Form
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.formidable.withOptions
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.modules.cms.RenderCtx
import de.peekandpoke.modules.cms.cmsAdminRoutes
import de.peekandpoke.modules.cms.db.cmsSnippets
import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.modules.cms.domain.CmsSnippet
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import de.peekandpoke.ultra.vault.Ref
import de.peekandpoke.ultra.vault.Stored
import io.ultra.ktor_tools.database
import kotlinx.html.FlowContent
import kotlinx.html.a

@Mutable
data class SnippetElement(
    var snippet: Ref<CmsSnippet>? = null
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "snippet-element"
    }

    override val elementName: String get() = "Snippet '${snippet?._key}': ${snippet?.value?.name}"

    override fun FlowContent.render(ctx: RenderCtx) {
        snippet?.value?.element?.apply { render(ctx) }
    }

    inner class VmForm(name: String, snippets: List<Stored<CmsSnippet>>) : Form(name) {

        val snippet = field(
            this@SnippetElement::snippet,
            { it?._key ?: "" },
            { snippets.firstOrNull { snippet -> snippet._key == it }?.asRef }
        ).withOptions(
            "---".untranslated(),
            snippets.map { it.asRef to it.value.name.untranslated() }
        )
    }

    override suspend fun editVm(vm: ViewModelBuilder, actions: CmsElement.EditActions): View {

        val allSnippets = vm.call.database.cmsSnippets.findAllSorted().toList()

        val form = VmForm(vm.path, allSnippets)

        if (form.submit(vm.call)) {
            actions.modify(this)
        }

        return vm.view {

            formidable(vm.call.i18n, form, { action = "#element.${actions.index}" }) {

                ui.attached.segment {

                    ui.header.given(form.isSubmitted() && form.isNotValid()) { red } H3 {
                        icon.quote_right()
                        +"Snippet '${snippet?.value?.name}'"
                    }

                    ui.divider {}

                    snippet?.apply {
                        icon.edit()
                        a(href = vm.route(vm.call.cmsAdminRoutes.editSnippet(asStored))) { +"Edit snippet '${value.name}'" }

                        ui.divider {}
                    }

                    ui.two.fields {
                        selectInput(form.snippet, "Snippet")
                    }
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
