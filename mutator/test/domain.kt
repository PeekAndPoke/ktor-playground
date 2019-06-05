package de.peekandpoke.mutator

@Mutator
data class WithScalars(
    val aString: String = "string",
    val aChar: Char = 'c',
    val aByte: Byte = 1,
    val aShort: Short = 1,
    val aInt: Int = 1,
    val aLong: Long = 1L,
    val aFloat: Float = 1.0f,
    val aDouble: Double = 1.0,
    val aBool: Boolean = true
)

// TODO: fix the problem in the annotation-processor TypeElement.variables when a property has a name collision with java type
//  like "int", "byte"  etc.
//  In these cases the bytecode for the class is "strange" and the annotation processor will not find the variables
//  Solution would be to look for specific getters "getInt()", "getByte()" ...
//  ..
//  OR: at least write something in the docs (Gotchas section)

@Mutator
data class Company(val name: String, val boss: Person)

data class Person(val name: String, val age: Int, val address: Address)

data class Address(val city: String, val zip: String)

@Mutator
data class ListOfAddress(val addresses: List<Address>)

