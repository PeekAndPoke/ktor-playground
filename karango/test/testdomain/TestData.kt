package de.peekandpoke.karango.testdomain

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.meta.EntityCollection

@EntityCollection("test")
data class TestData(
    val name: String,
    override val _id: String = ""
) : Entity

