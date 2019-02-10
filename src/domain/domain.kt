package de.peekandpoke.domain

import arrow.optics.optics
import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.meta.EntityCollection

@optics
@EntityCollection("persons")
data class Person(
    val name: String,
    val nr: Int,
    val address: Address,
    val books: List<String> = listOf(),
    override val _id: String = ""
) : Entity {
    companion object
}

@optics
@EntityCollection("addresses")
data class Address(
    val city: String,
    override val _id: String = ""
) : Entity {
    companion object
}
