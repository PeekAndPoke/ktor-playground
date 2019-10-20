package de.peekandpoke.jointhebase.fixtures

import de.peekandpoke.jointhebase.db.OrganisationsRepository
import de.peekandpoke.jointhebase.db.ResidentialPropertiesRepository
import de.peekandpoke.jointhebase.domain.Address
import de.peekandpoke.jointhebase.domain.ResidentialProperty

class ResidentialPropertiesFixtureLoader(
    private val organisations: OrganisationsRepository,
    private val properties: ResidentialPropertiesRepository
) : FixtureLoader {

    override fun load() {
        properties.removeAll()

        val joinTheBase = organisations.findById("jointhebase")!!

        properties.save(
            "JTB-Tempelhof",
            ResidentialProperty(
                joinTheBase.asRef,
                "Tempelhof",
                Address(country = "Deutschland", city = "Berlin", street = "ABC Straße", houseNumber = "123", zipCode = "01234")
            )
        )
    }
}
