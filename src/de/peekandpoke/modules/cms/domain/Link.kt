package de.peekandpoke.modules.cms.domain

import de.peekandpoke.ultra.mutator.Mutable

@Mutable
data class Link(
    val title: String,
    val url: String
)
