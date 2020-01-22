package com.thebase

import com.thebase.apps.admin.TheBaseAdmin
import com.thebase.apps.admin.TheBaseAdminRoutes
import com.thebase.apps.cms.TheBaseCmsModule
import com.thebase.db.OrganisationsRepository
import com.thebase.db.RentableRoomsRepository
import com.thebase.db.ResidentialPropertiesRepository
import com.thebase.fixtures.FixtureInstaller
import com.thebase.fixtures.OrganisationsFixtureLoader
import com.thebase.fixtures.RentableRoomFixtureLoader
import com.thebase.fixtures.ResidentialPropertiesFixtureLoader
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module

fun KontainerBuilder.theBase() = module(TheBaseModule)

val TheBaseModule = module {

    // Admin
    singleton(TheBaseAdminRoutes::class)
    singleton(TheBaseAdmin::class)

    // Database
    singleton(OrganisationsRepository::class)
    singleton(ResidentialPropertiesRepository::class)
    singleton(RentableRoomsRepository::class)

    // Cms
    singleton(TheBaseCmsModule::class)

    // Fixtures
    // TODO: only put here, when this is NOT a live environment
    singleton(FixtureInstaller::class)
    singleton(OrganisationsFixtureLoader::class)
    singleton(ResidentialPropertiesFixtureLoader::class)
    singleton(RentableRoomFixtureLoader::class)
}

