package de.peekandpoke.modules.cms.domain

import de.peekandpoke.ultra.mutator.Mutable

@Mutable
data class Link(
    val title: String,
    val url: String
) {

    val isInternalLink get(): Boolean = !isExternalLink && !isHashLink

    val isExternalLink
        get(): Boolean = normalizeUri(url).let {
            it.startsWith("http://") ||
                    it.startsWith("http://")
        }

    val isHashLink
        get() : Boolean = normalizeUri(url).startsWith("#")

    companion object {
        fun normalizeUri(uri: String) = uri.trimStart('/').trim()
    }

}
