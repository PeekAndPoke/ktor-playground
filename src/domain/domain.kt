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
    val books: List<Book> = listOf(),
    val favouriteBook: Book? = null,
    override val _id: String = ""
) : Entity {
    companion object
}

@EntityCollection("books")
data class Book(
    val title: String,
    val year: Int,
    val authors : List<Author>
)


@EntityCollection("authors")
data class Author (
    val firstName: String,
    val lastName: String
)

@optics
@EntityCollection("addresses")
data class Address(
    val city: String,
    override val _id: String = ""
) : Entity {
    companion object
}
