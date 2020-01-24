package com.thebase.apps.cms.elements

import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.modules.cms.RenderCtx
import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.modules.cms.domain.CmsSnippet
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import de.peekandpoke.ultra.vault.Ref
import kotlinx.html.FlowContent

@Mutable
data class SnippetElement(
    val snippet: Ref<CmsSnippet>? = null
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "snippet-element"
    }

    override fun FlowContent.render(ctx: RenderCtx) {

        snippet?.value?.element?.apply { render(ctx) }
    }

    override suspend fun editVm(vm: ViewModelBuilder, actions: CmsElement.EditActions): View {
        TODO("IMPLEMENT ME")
    }
}
