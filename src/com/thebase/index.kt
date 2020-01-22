package com.thebase

import com.thebase.admin.TheBaseAdmin
import com.thebase.admin.TheBaseAdminRoutes
import com.thebase.db.OrganisationsRepository
import com.thebase.db.RentableRoomsRepository
import com.thebase.db.ResidentialPropertiesRepository
import com.thebase.fixtures.FixtureInstaller
import com.thebase.fixtures.OrganisationsFixtureLoader
import com.thebase.fixtures.RentableRoomFixtureLoader
import com.thebase.fixtures.ResidentialPropertiesFixtureLoader
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module

fun KontainerBuilder.joinTheBase() = module(JoinTheBase)

val JoinTheBase = module {

    // Admin
    singleton(TheBaseAdminRoutes::class)
    singleton(TheBaseAdmin::class)

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

