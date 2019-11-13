package de.peekandpoke.jointhebase.db

import de.peekandpoke.jointhebase.domain.Organisation
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.vault.EntityRepository
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.common.kType
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.hooks.WithTimestamps
import de.peekandpoke.ultra.vault.hooks.WithUserRecord

val Database.organisations get() = getRepository(OrganisationsRepository::class.java)

val Organisations = EntityCollection<Organisation>("organisations", kType())

@WithTimestamps
@WithUserRecord
class OrganisationsRepository(driver: KarangoDriver) : EntityRepository<Organisation>(driver, Organisations)
