package de.peekandpoke.karango_dev.domain

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class Person(
    val name: String,
    val age: Int,
    val address: Address = Address("n/a"),
    val books: List<Book> = listOf(),
    val favouriteBook: Book? = null,
    override val _id: String = "",
    override val _key: String = ""
) : Entity {
    companion object
}

data class Book(
    val title: String,
    val year: Int,
    val authors: List<Author>
)

@Karango
@Mutable
data class Author(
    val firstName: String,
    val lastName: String
)

@Karango
@Mutable
data class Address(
    val city: String,
    override val _id: String = "",
    override val _key: String = ""
) : Entity {
    companion object
}
