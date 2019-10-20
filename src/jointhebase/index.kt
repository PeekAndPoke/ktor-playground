package de.peekandpoke.jointhebase

import de.peekandpoke.jointhebase.admin.JtbAdmin
import de.peekandpoke.jointhebase.admin.JtbAdminRoutes
import de.peekandpoke.jointhebase.db.OrganisationsRepository
import de.peekandpoke.jointhebase.db.RentableRoomsRepository
import de.peekandpoke.jointhebase.db.ResidentialPropertiesRepository
import de.peekandpoke.jointhebase.fixtures.FixtureInstaller
import de.peekandpoke.jointhebase.fixtures.OrganisationsFixtureLoader
import de.peekandpoke.jointhebase.fixtures.RentableRoomFixtureLoader
import de.peekandpoke.jointhebase.fixtures.ResidentialPropertiesFixtureLoader
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module

fun KontainerBuilder.joinTheBase() = module(JoinTheBase)

val JoinTheBase = module {

    // Admin
    singleton(JtbAdminRoutes::class)
    singleton(JtbAdmin::class)

    // Database
    singleton(OrganisationsRepository::class)
    singleton(ResidentialPropertiesRepository::class)
    singleton(RentableRoomsRepository::class)

    // Fixtures
    // TODO: only put here, when this is NOT a live environment
    singleton(FixtureInstaller::class)
    singleton(OrganisationsFixtureLoader::class)
    singleton(ResidentialPropertiesFixtureLoader::class)
    singleton(RentableRoomFixtureLoader::class)
}

