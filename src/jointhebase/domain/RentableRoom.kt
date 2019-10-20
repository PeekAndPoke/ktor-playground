package de.peekandpoke.jointhebase.domain

import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.vault.Ref
import de.peekandpoke.ultra.vault.Stored

@Mutable
@Karango
data class RentableRoom(
    val property: Ref<ResidentialProperty>,
    val nr: String,
    val squareMeters: Double,
    val floor: String
) {
    companion object {
        fun empty(property: Stored<ResidentialProperty>) = RentableRoom(
            property = property.asRef,
            nr = "",
            squareMeters = 0.0,
            floor = ""
        )
    }
}
