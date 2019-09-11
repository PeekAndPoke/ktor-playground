package de.peekandpoke.karango.examples.movies_and_actors

import de.peekandpoke.karango.Edge
import de.peekandpoke.karango.EdgeCollection
import de.peekandpoke.karango.aql.type

inline val ActsIn.Companion.Collection inline get() = ActsInCollection

object ActsInCollection : EdgeCollection<ActsIn>("actsIn", type())

data class ActsIn(
    override val _from: String,
    override val _to: String,
    val roles: List<String>,
    val year: Int,
    override val _id: String = "",
    override val _key: String = ""
) : Edge {

    @Suppress("unused") @Deprecated("Deserialization only", level = DeprecationLevel.HIDDEN)
    constructor() : this(_from = "", _to = "", roles = listOf<String>(), year = 0)

    companion object
}
