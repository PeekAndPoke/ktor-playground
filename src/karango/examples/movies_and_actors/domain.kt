package de.peekandpoke.karango.examples.movies_and_actors

import de.peekandpoke.karango.*
import de.peekandpoke.karango.aql.typeRef
import de.peekandpoke.karango.meta.EntityCollection

@EntityCollection("movies")
data class Movie constructor(
    val title: String,
    val released: Int,
    val tagline: String,
    override val _id: String = "",
    override val _key: String = "",
    override val _rev: String = ""
) : Entity, WithKey, WithRev {

    @Suppress("unused") @Deprecated("For Arrow::Optics only", level = DeprecationLevel.HIDDEN)
    constructor(title: String = "", released: Int = 0, tagline: String = "") :
            this(title = title, released = released, tagline = tagline)

    @Suppress("unused") @Deprecated("For Deserialization only", level = DeprecationLevel.HIDDEN)
    constructor() : this(title = "", released = 0, tagline = "")

    companion object
}

@EntityCollection("actors")
data class Actor(
    val name: String,
    val born: Int,
    override val _id: String = "",
    override val _key: String = "",
    override val _rev: String = ""
) : Entity, WithKey, WithRev {

    @Suppress("unused") @Deprecated("For Arrow::Optics only", level = DeprecationLevel.HIDDEN)
    constructor(name: String = "", born: Int = 0) :
            this(name = name, born = born)

    @Suppress("unused") @Deprecated("Deserialization only", level = DeprecationLevel.HIDDEN)
    constructor() : this(name = "", born = 0)

    companion object
}

inline val ActsIn.Companion.Collection inline get() = ActsInCollection

object ActsInCollection : EdgeCollectionDefinitionImpl<ActsIn>("actsIn", typeRef())

data class ActsIn(
    override val _from: String,
    override val _to: String,
    val roles: List<String>,
    val year: Int,
    override val _id: String = ""
) : Edge {

    @Suppress("unused") @Deprecated("Deserialization only", level = DeprecationLevel.HIDDEN)
    constructor() : this(_from = "", _to = "", roles = listOf<String>(), year = 0)

    companion object
}
