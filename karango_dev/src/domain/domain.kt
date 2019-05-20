package de.peekandpoke.karango_dev.domain

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.meta.EntityCollection


@EntityCollection("persons")
data class Person(
    val name: String,
    val age: Int,
//    val address: Address = Address("n/a"),
//    val books: List<Book> = listOf(),
//    val favouriteBook: Book? = null,
    override val _id: String = ""
) : Entity {
    companion object
}

data class Book(
    val title: String,
    val year: Int,
    val authors: List<Author>
)

@EntityCollection("authors")
data class Author(
    val firstName: String,
    val lastName: String
)

@EntityCollection("addresses")
data class Address(
    val city: String,
    override val _id: String = ""
) : Entity {
    companion object
}
