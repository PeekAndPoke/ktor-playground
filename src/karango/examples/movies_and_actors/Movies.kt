package de.peekandpoke.karango.examples.movies_and_actors

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.Karango
import de.peekandpoke.karango.WithRev
import de.peekandpoke.karango.aql.type
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class Movie constructor(
    val title: String,
    val released: Int,
    val tagline: String,
    override val _id: String = "",
    override val _key: String = "",
    override val _rev: String = ""
) : Entity, WithRev

val Movies = MoviesCollection()

class MoviesCollection : EntityCollection<Movie>("movies", type())
