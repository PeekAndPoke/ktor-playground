package de.peekandpoke.jointhebase.domain

data class Address(
    val country: String,
    val city: String,
    val street: String,
    val houseNumber: String,
    val zipCode: String
) {
    companion object {
        val empty = Address(
            country = "",
            city = "",
            street = "",
            houseNumber = "",
            zipCode = ""
        )
    }
}
