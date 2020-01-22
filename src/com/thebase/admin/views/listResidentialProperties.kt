package com.thebase.admin.views

import com.thebase.domain.ResidentialProperty
import de.peekandpoke.AdminTemplate
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.*

fun AdminTemplate.listResidentialProperties(properties: Iterable<Stored<ResidentialProperty>>) {

    breadCrumbs = listOf(TheBaseAdminMenu.INDEX, TheBaseAdminMenu.RESIDENTIAL_PROPERTIES)

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
                            a(href = theBase.routes.listRentableRooms(property)) {
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
