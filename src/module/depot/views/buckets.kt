package de.peekandpoke.module.depot.views

import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.depot.DepotAdmin
import de.peekandpoke.ultra.depot.DepotBucket
import de.peekandpoke.ultra.depot.DepotRepository
import kotlinx.html.*

internal fun SimpleTemplate.buckets(mod: DepotAdmin, repository: DepotRepository, bucketList: List<DepotBucket>) {

    breadCrumbs = listOf(DepotMenu.REPOSITORIES, DepotMenu.BUCKETS)

    pageHead {
        title { +"Depot Buckets" }
    }

    content {
        ui.dividing.header H1 {
            +"Buckets of ${repository.name}"

//            ui.right.floated.basic.primary.button A {
//                href = mod.routes.createPage
//                +"Create Page"
//            }
        }

        ui.celled.table Table {
            thead {
                tr {
                    th { +"Name" }
                    th { +"Last modified at" }
                }
            }

            tbody {
                bucketList.forEach {
                    tr {
                        td {
                            a(href = mod.routes.getBucket(repository.name, it.name)) { +it.name }
                        }
                        td {
                            +(it.lastModifiedAt?.toString() ?: "n/a")
                        }
                    }
                }
            }
        }
    }
}
