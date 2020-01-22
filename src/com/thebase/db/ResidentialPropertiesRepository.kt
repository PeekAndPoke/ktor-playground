package com.thebase.db

import com.thebase.domain.Organisation
import com.thebase.domain.ResidentialProperty
import com.thebase.domain.name
import com.thebase.domain.organisation
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.aql.ASC
import de.peekandpoke.karango.aql.EQ
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.vault.EntityRepository
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.common.kType
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.Stored
import de.peekandpoke.ultra.vault.hooks.WithTimestamps
import de.peekandpoke.ultra.vault.hooks.WithUserRecord

val Database.residentialProperties get() = getRepository(ResidentialPropertiesRepository::class.java)

val ResidentialProperties = EntityCollection<ResidentialProperty>("residential_properties", kType())

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
