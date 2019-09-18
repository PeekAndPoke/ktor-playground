package de.peekandpoke.module.cms.views

import de.peekandpoke.module.cms.CmsPage
import de.peekandpoke.ultra.vault.Stored
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.*

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal fun Template.pages(pages: List<Stored<CmsPage>>) {

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
                    th { +"Id" }
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
                            a(href = linkTo.editPage(it)) { +it._id }
                        }
                        td {
                            a(href = linkTo.editPage(it)) { +it.value.name }
                        }
                        td {
                            +(it.value._ts?.createdAt?.toString() ?: "n/a")
                        }
                        td {
                            +(it.value._ts?.updatedAt?.toString() ?: "n/a")
                        }
                        td {
                            +(it.value._userRecord?.user ?: "n/a")
                        }
                    }
                }

            }
        }

        ui.list {
        }
    }
}
