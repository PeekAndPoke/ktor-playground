package de.peekandpoke.karango.examples.game_of_thrones

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class Actor(
    val name: String,
    val surname: String,
    val age: Int = 0,
    override val _id: String? = null,
    override val _key: String? = null
) : Entity
