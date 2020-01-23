package de.peekandpoke.modules.cms.domain

import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class CmsSnippet(
    val name: String = "",
    val element: CmsElement = CmsElement.Empty
)
