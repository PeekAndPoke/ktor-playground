package com.thebase.apps.admin.views

import com.thebase.apps.admin.AdminTemplate
import com.thebase.domain.Organisation
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.*

fun AdminTemplate.index(organisations: Iterable<Stored<Organisation>>) {

    breadCrumbs = listOf(TheBaseAdminMenu.INDEX)

    content {
        ui.header H3 { +"Organisations" }

        ui.celled.table Table {
            thead {
                tr {
                    th { +"Id" }
                    th { +"Name" }
                    th { +"Created At" }
                }
            }

            tbody {
                organisations.forEach { org ->
                    tr {
                        td {
                            a(href = theBase.routes.listResidentialProperties(org)) { +org._key }
                        }
                        td {
                            +org.value.name
                        }
                        td {
                            +org._meta?.ts?.updatedAt.toString()
                        }
                    }
                }
            }
        }
    }
}
