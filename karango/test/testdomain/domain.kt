package de.peekandpoke.karango.testdomain

import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.Karango
import de.peekandpoke.karango.vault.EntityRepository
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.hooks.WithTimestamps
import de.peekandpoke.ultra.vault.hooks.WithUserRecord
import de.peekandpoke.ultra.vault.kType

@Karango
data class TestName(
    val name: String
)

val TestNames = EntityCollection<TestName>("test-names", kType())

@Karango
data class TestPerson(
    val name: String,
    val details: TestPersonDetails = TestPersonDetails(""),
    val addresses: List<TestAddress>,
    val books: List<TestBook> = listOf()
)

val TestPersons = EntityCollection<TestPerson>("test-persons", kType())

@WithTimestamps
@WithUserRecord
class TestPersonsRepository(driver: KarangoDriver) : EntityRepository<TestPerson>(driver, TestPersons)

val Database.testPersons get() = getRepository<TestPersonsRepository>()

data class TestPersonDetails(
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
