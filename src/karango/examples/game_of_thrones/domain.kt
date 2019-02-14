package de.peekandpoke.karango.examples.game_of_thrones

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.meta.EntityCollection

@EntityCollection("got_characters")
data class Character(
    val name: String, 
    val surname: String? = null,
    val alive: Boolean,
    val age: Int? = null,
    val traits: List<String>,
    override val _id: String = ""
) : Entity

