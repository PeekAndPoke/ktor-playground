package de.peekandpoke.modules.cms.views

import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.modules.cms.CmsAdminRoutes
import de.peekandpoke.modules.cms.domain.CmsSnippet
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.*

internal fun SimpleTemplate.snippets(routes: CmsAdminRoutes, pages: List<Stored<CmsSnippet>>) {

    breadCrumbs = listOf(CmsMenu.SNIPPETS)

    pageHead {
        title { +"CMS Snippets" }
    }

    content {
        ui.dividing.header H1 {
            +"Pages"

            ui.right.floated.basic.primary.button A {
                href = routes.createSnippet
                +"Create Snippet"
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
                            a(href = routes.editSnippet(it)) { +it._id }
                        }
                        td {
                            a(href = routes.editSnippet(it)) { +it.value.name }
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
