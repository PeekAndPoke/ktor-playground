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
interface CmsElement : CmsItem {

    /**
     * Slumber polymorphic configuration
     */
    companion object : Polymorphic.Parent {

        val Empty: CmsElement = EmptyElement()

        override val childTypes = CmsElement::class.indexedSubClasses

        override val defaultType: KClass<*> = Empty::class
    }

    class EmptyElement : CmsElement {
        override fun FlowContent.render(ctx: RenderCtx) {
            // noop
        }
    }

    /**
     * Actions passed to [editVm]
     */
    interface EditActions {

        val index: Int get() = 0

        fun modify(it: CmsElement): Nothing {
            error("not implemented")
        }

        fun delete(): Nothing {
            error("not implemented")
        }

        fun addBefore(it: CmsElement): Nothing {
            error("not implemented")
        }

        fun addAfter(it: CmsElement): Nothing {
            error("not implemented")
        }

        fun moveUp(it: CmsElement): Nothing {
            error("not implemented")
        }

        fun moveDown(it: CmsElement): Nothing {
            error("not implemented")
        }
    }

    val name: String get() = this::class.simpleName ?: "n/a"

    suspend fun editVm(vm: ViewModelBuilder, actions: EditActions): View = vm.view {
        div {
            style = "background-color: pink; padding: 20px; margin-bottom: 2px;"

            +"editVm() is not implemented for CmsElement '${this@CmsElement::class.qualifiedName}'"
        }
    }
}
