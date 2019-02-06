package de.peekandpoke

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.EntityCollectionDef

object Persons : EntityCollectionDef<Person>("persons", Person::class) {
    val name = string("name")
    val nr = number("nr")
    val city = string("city")

    val books = array<String>("books")
}

data class Person(
    override val _id: String? = null,
    val name: String,
    val nr: Int,
    val city: String = "",
    val books: List<String> = listOf()
) : Entity

object Addresses : EntityCollectionDef<Address>("addresses", Address::class) {
    val city = string("city")
}

data class Address(
    override val _id: String? = null,
    val city: String
) : Entity
