package de.peekandpoke.karango.examples.game_of_thrones

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.WithKey
import de.peekandpoke.karango.meta.EntityCollection
import de.peekandpoke.karango.meta.Ref
import de.peekandpoke.mutator.Mutator

@Mutator
@EntityCollection("got_characters", "Characters")
data class Character(
    val name: String,
    val surname: String? = null,
    val alive: Boolean,
    val age: Int? = null,
    val traits: List<String>,
    @Ref val actor: Actor? = null,
    override val _id: String? = null,
    override val _key: String? = null
) : Entity, WithKey {

//    fun fullName() = listOfNotNull(name, surname).joinToString(" ")

    val fullName by lazy { listOfNotNull(name, surname).joinToString(" ") }
}

@EntityCollection("got_actors", "Actors")
data class Actor(
    val name: String,
    val surname: String,
    val age: Int = 0,
    override val _id: String? = null,
    override val _key: String? = null
) :  Entity, WithKey
