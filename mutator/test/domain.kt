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

@Mutator
data class WithNullableScalars(
    val aString: String? = null,
    val aChar: Char? = null,
    val aByte: Byte? = null,
    val aShort: Short? = null,
    val aInt: Int? = null,
    val aLong: Long? = null,
    val aFloat: Float? = null,
    val aDouble: Double? = null,
    val aBool: Boolean? = null
)

@Mutator
data class WithAnyObject(
    val anObject: Any
)

@Mutator
data class WithAnyNullableObject(
    val anObject: Any?
)

@Mutator
data class Company(val name: String, val boss: Person)

data class Person(val name: String, val age: Int, val address: Address)

data class Address(val city: String, val zip: String)

@Mutator
data class ListOfAddresses(val addresses: List<Address>)

@Mutator
data class SetOfAddresses(val addresses: Set<Address>)

@Mutator
data class MapOfAddresses(val addresses: Map<String, Address>)

@Mutator
data class ListOfBools(val values: List<Boolean>)

@Mutator
data class ListOfChars(val values: List<Char>)

@Mutator
data class ListOfBytes(val values: List<Byte>)

@Mutator
data class ListOfShorts(val values: List<Short>)

@Mutator
data class ListOfInts(val values: List<Int>)

@Mutator
data class ListOfNullableInts(val values: List<Int?>)

@Mutator
data class ListOfLongs(val values: List<Long>)

@Mutator
data class ListOfFloats(val values: List<Float>)

@Mutator
data class ListOfDoubles(val values: List<Double>)

@Mutator
data class ListOfStrings(val values: List<String>)
