package de.peekandpoke.module.cms.elements

import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import org.atteo.classindex.ClassIndex
import org.atteo.classindex.IndexSubclasses
import kotlin.reflect.KClass

interface CmsItem<T> {

    fun canHandle(data: T): Boolean

    fun FlowContent.render(data: T)
}

abstract class CmsElement<T : CmsElement.Data>(private val cls: KClass<T>) : CmsItem<T> {

    @IndexSubclasses
    interface Data {
        /**
         * Slumber polymorphic configuration
         */
        companion object : Polymorphic.Parent {
            override val childTypes: List<KClass<*>>
                get() = ClassIndex.getSubclasses(Data::class.java).map { it.kotlin }
        }
    }

    override fun canHandle(data: T): Boolean = data::class == cls
}

abstract class CmsLayout<T : CmsLayout.Data>(private val cls: KClass<T>) : CmsItem<T> {

    @IndexSubclasses
    interface Data {
        /**
         * Slumber polymorphic configuration
         */
        companion object : Polymorphic.Parent {
            override val childTypes: List<KClass<*>>
                get() = ClassIndex.getSubclasses(Data::class.java).map { it.kotlin }
        }
    }

    override fun canHandle(data: T): Boolean = data::class == cls
}
