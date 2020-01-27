package com.thebase.apps.cms.elements

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
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
    val snippet: Ref<CmsSnippet>? = null
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "snippet-element"
    }

    override val name: String get() = "Snippet '${snippet?._key}': ${snippet?.value?.name}"

    override fun FlowContent.render(ctx: RenderCtx) {
        snippet?.value?.element?.apply { render(ctx) }
    }

    inner class VmForm(name: String, snippets: List<Stored<CmsSnippet>>) : MutatorForm<SnippetElement, SnippetElementMutator>(mutator(), name) {

        val snippet = field(
            target::snippet,
            { it?._key ?: "" },
            { snippets.firstOrNull { snippet -> snippet._key == it }?.asRef }
        ).withOptions(
            "---".untranslated(),
            snippets.map { it.asRef to it.value.name.untranslated() }
        )
    }

    //    class VmForm(name: String, element: SnippetElement, snippets: List<Stored<CmsSnippet>>) : Form(name) {
//
//        data class VmFormData(var snippet: Ref<CmsSnippet>?)
//
//        val data = VmFormData(element.snippet)
//
//        val snippet = field(
//            data::snippet,
//            { it?._key ?: "" },
//            { snippets.firstOrNull { snippet -> snippet._key == it }?.asRef }
//        ).withOptions(
//            "---".untranslated(),
//            snippets.map { it.asRef to it.value.name.untranslated() }
//        )
//    }
//
    override suspend fun editVm(vm: ViewModelBuilder, actions: CmsElement.EditActions): View {

        val allSnippets = vm.call.database.cmsSnippets.findAllSorted().toList()

        val form = VmForm(vm.path, allSnippets)

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
                        +"Snippet '${snippet?.value?.name}'"
                    }

                    ui.divider {}

                    if (snippet != null) {
                        icon.edit()
                        a(href = vm.call.cmsAdminRoutes.editSnippet(snippet.asStored)) { +"Edit snippet '${snippet.value.name}'" }

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
