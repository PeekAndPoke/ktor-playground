package de.peekandpoke.module.cms.views

import de.peekandpoke.module.cms.CmsPage
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.*

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal fun Template.pages(pages: List<CmsPage>) {

    activeMenu = MenuEntries.PAGES

    content {
        ui.dividing.header H1 {
            +"Pages"

            ui.right.floated.basic.primary.button A {
                href = linkTo.createPage()
                +"Create Page"
            }
        }


        ui.celled.table Table {
            thead {
                tr {
                    th { +"Name" }
                    th { +"Created at" }
                    th { +"Updated at" }
                    th { +"Last edit by" }
                }
            }

            tbody {
                pages.forEach {
                    tr {
                        td {
                            a(href = linkTo.editPage(it)) { +it.name }
                        }
                        td {
                            +(it._ts?.createdAt?.toString() ?: "n/a")
                        }
                        td {
                            +(it._ts?.updatedAt?.toString() ?: "n/a")
                        }
                        td {
                            +(it._userRecord?.user ?: "n/a")
                        }
                    }
                }

            }
        }

        ui.list {
        }
    }
}
