package de.peekandpoke.karango.examples.movies_and_actors

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.Karango
import de.peekandpoke.karango.WithRev
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.vault.type

@Karango
@Mutable
data class Actor(
    val name: String,
    val born: Int,
    override val _id: String = "",
    override val _key: String = "",
    override val _rev: String = ""
) : Entity, WithRev

val Actors = ActorsCollection()

class ActorsCollection : EntityCollection<Actor>("actors", type())
