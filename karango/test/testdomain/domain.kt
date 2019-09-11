package de.peekandpoke.karango.testdomain

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.IEntityCollection
import de.peekandpoke.karango.Karango
import de.peekandpoke.karango.aql.type

@Karango
data class TestName(
    val name: String,
    override val _id: String = "",
    override val _key: String = ""
) : Entity

val TestNames : IEntityCollection<TestName> =
    object : EntityCollection<TestName>("test-names", type()) {}

@Karango
data class TestPerson(
    val name: String,
    val details: TestPersonDetails = TestPersonDetails(""),
    val addresses: List<TestAddress>,
    val books: List<TestBook> = listOf()
)

val TestPersons : IEntityCollection<TestPerson> =
    object : EntityCollection<TestPerson>("test-persons", type()) {}

data class TestPersonDetails (
    val middleName: String
)

data class TestAddress(
    val street: String,
    val number: String,
    val city: String,
    val zip: String
)

data class TestBook(
    val title: String,
    val authors: List<TestAuthor> = listOf()
)

data class TestAuthor(
    val firstName: String,
    val lastName: String
)
