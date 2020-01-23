package de.peekandpoke.modules.cms.domain

import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.modules.cms.RenderCtx
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.style
import org.atteo.classindex.IndexSubclasses
import kotlin.reflect.KClass

@IndexSubclasses
interface CmsLayout : CmsItem {

    /**
     * Slumber polymorphic configuration
     */
    companion object : Polymorphic.Parent {

        val Empty: CmsLayout = EmptyLayout()

        override val childTypes = CmsLayout::class.indexedSubClasses

        override val defaultType: KClass<*> = Empty::class
    }

    /**
     * Empty default layout
     */
    data class EmptyLayout(
        override val elements: List<CmsElement> = listOf()
    ) : CmsLayout {

        companion object : Polymorphic.Child {
            override val identifier = "empty-layout"
        }

        override fun withElements(elements: List<CmsElement>): EmptyLayout = copy(elements = elements)

        override fun FlowContent.render(ctx: RenderCtx) {
            // noop
        }
    }

    /**
     * The list of cms elements on the page.
     */
    val elements: List<CmsElement>

    /**
     * Creates a copy of the layout and sets the elements.
     */
    fun withElements(elements: List<CmsElement>): CmsLayout

    suspend fun editVm(vm: ViewModelBuilder, onChange: (CmsLayout) -> Nothing): View = vm.view {
        div {
            style = "background-color: pink; padding: 20px; margin-bottom: 2px;"

            +"editVm() is not implemented for CmsElement '${this@CmsLayout::class.qualifiedName}'"
        }
    }
}
