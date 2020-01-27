package de.peekandpoke.modules.cms

import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.modules.cms.domain.CmsLayout
import kotlin.reflect.KClass

class Cms(
    modules: List<Module>,
    val errorPages: CmsErrorPages,
    val markdown: SemanticMarkdown
) {
    abstract class Module(
        val layouts: Map<KClass<out CmsLayout>, CmsLayout>,
        val elements: Map<KClass<out CmsElement>, CmsElement>
    )

    /**
     * All available layouts
     */
    val layouts: Map<KClass<out CmsLayout>, CmsLayout> = modules.flatMap { it.layouts.toList() }.toMap()

    /**
     * All available elements
     */
    val elements: Map<KClass<out CmsElement>, CmsElement> = modules.flatMap { it.elements.toList() }.toMap()

    /**
     * Get a layout by its class
     */
    fun getLayout(cls: String): CmsLayout {

        return layouts.toList()
            .filter { it.first.qualifiedName == cls }
            .map { it.second }
            .firstOrNull() ?: CmsLayout.Empty
    }

    /**
     * Get an element by its class
     */
    fun getElement(cls: String): CmsElement {

        return elements.toList()
            .filter { it.first.qualifiedName == cls }
            .map { it.second }
            .firstOrNull() ?: CmsElement.Empty
    }
}

