package de.peekandpoke.jointhebase.admin.views

import de.peekandpoke.AdminTemplate
import de.peekandpoke.jointhebase.domain.RentableRoom
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.*

fun AdminTemplate.listRentableRooms(rooms: Iterable<Stored<RentableRoom>>) {

    breadCrumbs = listOf(JtbAdminMenu.INDEX, JtbAdminMenu.RENTABLE_ROOMS)

    content {
        ui.header H3 { +"List of properties" }

        ui.celled.table Table {

            thead {
                tr {
                    th { +"Id" }
                    th { +"Nr" }
                    th { +"Floor" }
                    th { +"m2" }
                }
            }

            tbody {
                rooms.forEach { room ->
                    tr {
                        td { +room._key }
                        td { +room.value.nr }
                        td { +room.value.floor }
                        td { +room.value.squareMeters.toString() }
                    }
                }
            }
        }
    }
}
