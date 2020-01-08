package de.peekandpoke.module.cms

import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import org.atteo.classindex.IndexSubclasses
import kotlin.reflect.KClass

data class Cms(
    val layouts: Map<KClass<out CmsLayout>, CmsLayout>
) {

    fun getLayout(cls: String): CmsLayout {

        return layouts.toList()
            .filter { it.first.qualifiedName == cls }
            .map { it.second }
            .firstOrNull() ?: CmsLayout.Empty
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

    class EmptyLayout : CmsLayout {
        override fun FlowContent.render() {
            // noop
        }
    }
}
