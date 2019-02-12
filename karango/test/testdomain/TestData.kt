package de.peekandpoke.karango.testdomain

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.meta.EntityCollection

@EntityCollection("test")
data class TestData(
    val name: String,
    override val _id: String = ""
) : Entity


@EntityCollection("test-persons")
data class TestPerson(
    val name: String,
    val addresses: List<TestAddress>
)

data class TestAddress(
    val street: String,
    val number: String,
    val city: String,
    val zip: String
)
