package de.peekandpoke.modules.cms.views

import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.modules.cms.CmsAdminRoutes
import de.peekandpoke.modules.cms.domain.CmsPage
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.*

internal fun SimpleTemplate.pages(routes: CmsAdminRoutes, pages: List<Stored<CmsPage>>) {

    breadCrumbs = listOf(CmsMenu.PAGES)

    pageHead {
        title { +"CMS Pages" }
    }

    content {
        ui.dividing.header H1 {
            +"Pages"

            ui.right.floated.basic.primary.button A {
                href = routes.createPage
                +"Create Page"
            }
        }

        ui.celled.table Table {
            thead {
                tr {
                    th { +"Id" }
                    th { +"Name" }
                    th { +"Slug" }
                    th { +"Created at" }
                    th { +"Updated at" }
                    th { +"Last edit by" }
                }
            }

            tbody {
                pages.forEach {
                    tr {
                        td {
                            a(href = routes.editPage(it)) { +it._id }
                        }
                        td {
                            a(href = routes.editPage(it)) { +it.value.name }
                        }
                        td {
                            a(href = routes.editPage(it)) { +it.value.slug }
                        }
                        td {
                            +(it._meta?.ts?.createdAt?.toString() ?: "n/a")
                        }
                        td {
                            +(it._meta?.ts?.updatedAt?.toString() ?: "n/a")
                        }
                        td {
                            +(it._meta?.user?.userId ?: "n/a")
                        }
                    }
                }

            }
        }
    }
}