package de.peekandpoke.karango.examples.movies_and_actors

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.WithKey
import de.peekandpoke.karango.WithRev
import de.peekandpoke.karango.aql.type
import de.peekandpoke.karango.meta.Karango
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class Actor(
    val name: String,
    val born: Int,
    override val _id: String = "",
    override val _key: String = "",
    override val _rev: String = ""
) : Entity, WithKey, WithRev

val Actors = ActorsCollection()

class ActorsCollection : EntityCollection<Actor>("actors", type())
