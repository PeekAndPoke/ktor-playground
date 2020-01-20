package de.peekandpoke.module.cms

import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.style
import org.atteo.classindex.IndexSubclasses
import kotlin.reflect.KClass

data class Cms(
    val layouts: Map<KClass<out CmsLayout>, CmsLayout>,
    val elements: Map<KClass<out CmsElement>, CmsElement>
) {

    fun getLayout(cls: String): CmsLayout {

        return layouts.toList()
            .filter { it.first.qualifiedName == cls }
            .map { it.second }
            .firstOrNull() ?: CmsLayout.Empty
    }

    fun getElement(cls: String): CmsElement {

        return elements.toList()
            .filter { it.first.qualifiedName == cls }
            .map { it.second }
            .firstOrNull() ?: CmsElement.Empty
    }
}

interface CmsItem {
    fun FlowContent.render()
}

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
        override fun FlowContent.render() {
            // noop
        }
    }

    /**
     * Actions passed to [editVm]
     */
    interface EditActions {
        fun modify(it: CmsElement): Nothing

        fun delete(): Nothing

        fun addBefore(it: CmsElement): Nothing

        fun addAfter(it: CmsElement): Nothing
    }

    suspend fun editVm(vm: ViewModelBuilder, actions: EditActions): View = vm.view {
        div {
            style = "background-color: pink; padding: 20px; margin-bottom: 2px;"

            +"editVm() is not implemented for CmsElement '${this@CmsElement::class.qualifiedName}'"
        }
    }
}

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

        override fun FlowContent.render() {
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
