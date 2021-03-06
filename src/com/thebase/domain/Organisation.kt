package com.thebase.domain

import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable

@Mutable
@Karango
data class Organisation(
    val name: String
)

