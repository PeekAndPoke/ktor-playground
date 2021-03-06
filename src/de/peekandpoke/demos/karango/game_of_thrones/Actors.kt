package de.peekandpoke.demos.karango.game_of_thrones

import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class Actor(
    val name: String,
    val surname: String,
    val age: Int = 0
)
