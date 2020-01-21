package de.peekandpoke.module.depot.views

import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.depot.DepotAdmin
import de.peekandpoke.ultra.depot.DepotBucket
import de.peekandpoke.ultra.depot.DepotFile
import de.peekandpoke.ultra.depot.DepotRepository
import kotlinx.html.*

internal fun SimpleTemplate.files(mod: DepotAdmin, repository: DepotRepository, bucket: DepotBucket, fileList: List<DepotFile>) {

    breadCrumbs = listOf(DepotMenu.REPOSITORIES, DepotMenu.BUCKETS, DepotMenu.FILES)

    pageHead {
        title { +"Depot Files" }
    }

    content {
        ui.dividing.header H1 {
            +"Files in ${repository.name}::${bucket.name}"

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
                fileList.forEach {
                    tr {
                        td {
                            a(href = mod.routes.getFile(repository.name, bucket.name, it.name)) { +it.name }
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
