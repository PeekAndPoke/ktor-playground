package de.peekandpoke.demos.movies_and_actors

import de.peekandpoke.karango.Edge
import de.peekandpoke.karango.EdgeCollectionDef
import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.EntityCollectionDef


object Movies : EntityCollectionDef<Movie>("movies", Movie::class.java) {
    val _id = string("_id")
    val _key = string("_key")
    val title = string("city")
    val released = number("released")
    val tagline = string("tagline")
}

data class Movie(
    override val _id: String? = null,
    val _key: String,
    val title: String,
    val released: Int,
    val tagline: String
) : Entity

object Actors : EntityCollectionDef<Actor>("actors", Actor::class.java) {
    val _id = string("_id")
    val _key = string("_key")
    val name = string("name")   
    val born = number("born")   
}

data class Actor(
    override val _id: String? = null,
    val _key: String,
    val name: String,
    val born: Int
) : Entity

object ActsInEdges : EdgeCollectionDef<ActsIn>("actsIn", ActsIn::class.java) {
    val _id = string("_id")
    val _from = string("_from")
    val _to = string("_to")
    val roles = array<String>("roles")
    val year = number("year")
}

data class ActsIn (
    override val _id: String? = null,
    override val _from: String,
    override val _to: String,
    val roles: List<String>,
    val year: Int
) : Edge
