package com.thebase.db

import com.thebase.domain.RentableRoom
import com.thebase.domain.ResidentialProperty
import com.thebase.domain.nr
import com.thebase.domain.property
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


val Database.rentableRooms get() = getRepository(RentableRoomsRepository::class.java)

val RentableRooms = EntityCollection<RentableRoom>("rentable_rooms", kType())

@WithTimestamps
@WithUserRecord
class RentableRoomsRepository(driver: KarangoDriver) : EntityRepository<RentableRoom>(driver, RentableRooms) {

    fun findByResidentialProperty(property: Stored<ResidentialProperty>) = find {
        FOR(coll) { r ->
            FILTER(r.property EQ property._id)
            SORT(r.nr.ASC)
            RETURN(r)
        }
    }
}
