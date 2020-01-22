package com.thebase.fixtures

import com.thebase.db.RentableRoomsRepository
import com.thebase.db.ResidentialPropertiesRepository
import com.thebase.domain.RentableRoom

class RentableRoomFixtureLoader(
    private val properties: ResidentialPropertiesRepository,
    private val rooms: RentableRoomsRepository
) : FixtureLoader {

    override fun load() {
        rooms.removeAll()

        val tjbTempelhof = properties.findById("JTB-Tempelhof")!!

        (1..7).forEach { floorNr ->

            val floorPadded = floorNr.toString().padStart(2, '0')

            (1..30).forEach { roomNr ->

                val roomPadded = roomNr.toString().padStart(3, '0')

                val room = "$floorPadded-$roomPadded"

                rooms.save("JTB-Tempelhof-$room", RentableRoom(tjbTempelhof.asRef, room, 16.5, floorNr.toString()))
            }
        }
    }
}
