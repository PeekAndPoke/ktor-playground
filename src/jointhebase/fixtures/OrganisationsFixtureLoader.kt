package de.peekandpoke.jointhebase.fixtures

import de.peekandpoke.jointhebase.db.OrganisationsRepository
import de.peekandpoke.jointhebase.domain.Organisation

class OrganisationsFixtureLoader(private val organisations: OrganisationsRepository) : FixtureLoader {

    override fun load() {
        organisations.removeAll()

        organisations.save("jointhebase", Organisation("JoinTheBase"))
    }
}
