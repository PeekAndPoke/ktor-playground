package de.peekandpoke.module.cms

import de.peekandpoke.karango.Karango
import de.peekandpoke.module.cms.elements.CmsLayout
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class CmsPage(
    val name: String,
    val slug: String,
    val data: CmsLayout.Data? = null
) {
    companion object {
        fun empty() = CmsPage(name = "", slug = "")
    }
}
