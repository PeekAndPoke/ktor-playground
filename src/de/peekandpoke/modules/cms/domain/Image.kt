package de.peekandpoke.modules.cms.domain

import de.peekandpoke.ultra.mutator.Mutable

@Mutable
data class Image(
    val url: String = "",
    val alt: String = ""
)
