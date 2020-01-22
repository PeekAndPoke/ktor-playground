package com.thebase.fixtures

import com.thebase.db.OrganisationsRepository
import com.thebase.db.ResidentialPropertiesRepository
import com.thebase.domain.Address
import com.thebase.domain.ResidentialProperty

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
