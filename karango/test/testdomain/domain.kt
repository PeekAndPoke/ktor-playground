package de.peekandpoke.karango.testdomain

import de.peekandpoke.karango.*
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
    val books: List<TestBook> = listOf(),
    override val _id: String? = null,
    override val _key: String? = null
) : Entity

val TestPersons : IEntityCollection<TestPerson> =
    object : EntityCollection<TestPerson>("test-persons", type()) {}

class TestPersonsCollection(db: Db) : DbEntityCollection<TestPerson>(db, TestPersons)

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
