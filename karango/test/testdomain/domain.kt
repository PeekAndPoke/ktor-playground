package de.peekandpoke.karango.testdomain

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.meta.Karango

@Karango("test-names", "TestNames")
data class TestName(
    val name: String,
    override val _id: String = ""
) : Entity



@Karango("test-persons", "TestPersons")
data class TestPerson(
    val name: String,
    val details: TestPersonDetails = TestPersonDetails(""),
    val addresses: List<TestAddress>,
    val books: List<TestBook> = listOf()
)

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
