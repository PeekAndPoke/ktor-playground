package de.peekandpoke.module.depot.views

import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.depot.DepotAdmin
import de.peekandpoke.ultra.depot.DepotRepository
import kotlinx.html.*

internal fun SimpleTemplate.repositories(mod: DepotAdmin, repositories: List<DepotRepository>) {

    breadCrumbs = listOf(DepotMenu.REPOSITORIES)

    pageTitle {
        title { +"Depot Repositories" }
    }

    content {
        ui.dividing.header H1 {
            +"Repositories"

//            ui.right.floated.basic.primary.button A {
//                href = mod.routes.createPage
//                +"Create Page"
//            }
        }

        ui.celled.table Table {
            thead {
                tr {
                    th { +"Name" }
                    th { +"Type" }
                }
            }

            tbody {
                repositories.forEach {
                    tr {
                        td {
                            a(href = mod.routes.getDriver(it.name)) { +it.name }
                        }
                        td {
                            +it.type
                        }
                    }
                }
            }
        }
    }
}
