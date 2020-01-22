package com.thebase.domain

import de.peekandpoke.ultra.mutator.Mutable

@Mutable
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
