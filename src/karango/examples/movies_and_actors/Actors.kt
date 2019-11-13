package de.peekandpoke.karango.examples.movies_and_actors

import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.common.kType
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class Actor(
    val name: String,
    val born: Int
)

val Actors = EntityCollection<Actor>("actors", kType())
