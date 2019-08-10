package de.peekandpoke.karango.examples.game_of_thrones

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.WithKey
import de.peekandpoke.karango.aql.type
import de.peekandpoke.karango.meta.Karango
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class Actor(
    val name: String,
    val surname: String,
    val age: Int = 0,
    override val _id: String? = null,
    override val _key: String? = null
) : Entity, WithKey

val Actors = ActorsCollection()

class ActorsCollection : EntityCollection<Actor>("got_actors", type())
