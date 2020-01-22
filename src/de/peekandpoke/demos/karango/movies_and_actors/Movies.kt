package de.peekandpoke.demos.karango.movies_and_actors

import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.common.kType
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class Movie constructor(
    val title: String,
    val released: Int,
    val tagline: String
)

val Movies = EntityCollection<Movie>("movies", kType())
