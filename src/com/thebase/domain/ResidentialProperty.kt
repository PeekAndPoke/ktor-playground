package com.thebase.domain

import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.vault.Ref
import de.peekandpoke.ultra.vault.Stored

@Mutable
@Karango
data class ResidentialProperty(
    val organisation: Ref<Organisation>,
    val name: String,
    val address: Address
) {
    companion object {

        fun empty(organisation: Stored<Organisation>) = ResidentialProperty(
            organisation = organisation.asRef,
            name = "",
            address = Address.empty
        )
    }
}
