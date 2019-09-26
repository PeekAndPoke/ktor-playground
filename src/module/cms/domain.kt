package de.peekandpoke.module.cms

import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class CmsPage(
    val name: String,
    val slug: String,
    val markup: String
) {
    companion object {
        fun empty() = CmsPage(name = "", slug = "", markup = "")
    }
}
