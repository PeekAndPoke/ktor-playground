package de.peekandpoke.modules.cms.domain

import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class CmsPage(
    val name: String,
    val uri: String,
    val layout: CmsLayout = CmsLayout.Empty
) {

    val isHomepage get() = Link.normalizeUri(uri).isEmpty()

    fun matchesUri(uri: String): Boolean {
        return Link.normalizeUri(uri) == this.uri.trimStart('/').trim()
    }

    companion object {
        fun empty() = CmsPage(name = "", uri = "")
    }
}
