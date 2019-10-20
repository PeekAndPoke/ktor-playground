package de.peekandpoke.jointhebase.db

import de.peekandpoke.jointhebase.domain.Organisation
import de.peekandpoke.jointhebase.domain.ResidentialProperty
import de.peekandpoke.jointhebase.domain.name
import de.peekandpoke.jointhebase.domain.organisation
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.aql.ASC
import de.peekandpoke.karango.aql.EQ
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.vault.EntityRepository
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.Stored
import de.peekandpoke.ultra.vault.hooks.WithTimestamps
import de.peekandpoke.ultra.vault.hooks.WithUserRecord
import de.peekandpoke.ultra.vault.type

val Database.residentialProperties get() = getRepository(ResidentialPropertiesRepository::class.java)

val ResidentialProperties = EntityCollection<ResidentialProperty>("residential_properties", type())

@WithTimestamps
@WithUserRecord
class ResidentialPropertiesRepository(driver: KarangoDriver) : EntityRepository<ResidentialProperty>(driver, ResidentialProperties) {

    fun findByOrganisation(organisation: Stored<Organisation>) = find {
        FOR(coll) { r ->
            FILTER(r.organisation EQ organisation._id)
            SORT(r.name.ASC)
            RETURN(r)
        }
    }
}
