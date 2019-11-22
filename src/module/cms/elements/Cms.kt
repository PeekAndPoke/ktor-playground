package de.peekandpoke.module.cms.elements

import kotlinx.html.FlowContent
import kotlin.reflect.KClass

class Cms(
    layouts: Lazy<List<CmsLayout<CmsLayout.Data>>>,
    elements: Lazy<List<CmsElement<CmsElement.Data>>>
) {

    private val layouts by lazy { layouts.value }
    private val elements by lazy { elements.value }

    private val layoutLookUp = mutableMapOf<KClass<out CmsLayout.Data>, CmsLayout<CmsLayout.Data>?>()
    private val elementLookUp = mutableMapOf<KClass<out CmsElement.Data>, CmsElement<CmsElement.Data>?>()

    fun FlowContent.render(data: CmsLayout.Data) {
        getLayoutForData<CmsLayout.Data>(data)?.apply { render(data) }
    }

    fun FlowContent.render(data: CmsElement.Data) {
        getElementForData<CmsElement.Data>(data)?.apply { render(data) }
    }

    fun FlowContent.render(data: List<CmsElement.Data>) {
        data.forEach {
            apply { render(it) }
        }
    }

    fun <T : CmsLayout.Data> getLayoutForData(data: CmsLayout.Data): CmsLayout<T>? {

        @Suppress("UNCHECKED_CAST")
        return layoutLookUp.getOrPut(data::class) {
            layouts.asSequence().firstOrNull { it.canHandle(data) }
        } as CmsLayout<T>?
    }

    fun <T : CmsElement.Data> getElementForData(data: CmsElement.Data): CmsElement<T>? {

        @Suppress("UNCHECKED_CAST")
        return elementLookUp.getOrPut(data::class) {
            elements.asSequence().firstOrNull { it.canHandle(data) }
        } as CmsElement<T>?
    }
}
