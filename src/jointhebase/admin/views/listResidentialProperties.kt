package de.peekandpoke.jointhebase.admin.views

import de.peekandpoke.AdminTemplate
import de.peekandpoke.jointhebase.domain.ResidentialProperty
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.*

fun AdminTemplate.listResidentialProperties(properties: Iterable<Stored<ResidentialProperty>>) {

    breadCrumbs = listOf(JtbAdminMenu.INDEX, JtbAdminMenu.RESIDENTIAL_PROPERTIES)

    content {
        ui.header H3 { +"List of properties" }

        ui.celled.table Table {

            thead {
                tr {
                    th { +"Id" }
                    th { +"Name" }
                    th { +"Address" }
                }
            }

            tbody {
                properties.forEach { property ->
                    tr {
                        td {
                            a(href = jtb.routes.listRentableRooms(property)) {
                                +property._key
                            }
                        }
                        td { +property.value.name }
                        td { +property.value.address.toString() }
                    }
                }
            }
        }
    }
}
