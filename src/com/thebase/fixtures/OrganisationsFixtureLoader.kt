package com.thebase.fixtures

import com.thebase.db.OrganisationsRepository
import com.thebase.domain.Organisation

class OrganisationsFixtureLoader(private val organisations: OrganisationsRepository) : FixtureLoader {

    override fun load() {
        organisations.removeAll()

        organisations.save("jointhebase", Organisation("JoinTheBase"))
    }
}
