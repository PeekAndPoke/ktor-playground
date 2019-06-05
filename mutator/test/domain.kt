package de.peekandpoke.mutator


@Mutator
data class SimplePerson(val name: String, val age: Int)

@Mutator
data class PersonWithAddress(val name: String, val age: Int, val address: Address)

data class Address(val city: String, val zip: String)
