package de.peekandpoke.modules.cms.views

import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.modules.cms.Cms
import de.peekandpoke.modules.cms.CmsAdminRoutes
import de.peekandpoke.modules.cms.domain.CmsSnippet
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.*

internal fun SimpleTemplate.snippets(routes: CmsAdminRoutes, pages: List<Stored<CmsSnippet>>, cms: Cms) {

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
                    th { +"Updated at" }
                    th { +"Last edit by" }
                    th {}
                }
            }

            tbody {
                pages.forEach {
                    tr {
                        td {
                            a(href = routes.editSnippet(it).url) { +it._id }
                        }
                        td {
                            a(href = routes.editSnippet(it).url) { +it.value.name }
                        }
                        td {
                            +(it._meta?.ts?.updatedAt?.toString() ?: "n/a")
                        }
                        td {
                            +(it._meta?.user?.userId ?: "n/a")
                        }
                        td {
                            if (cms.canDelete(it)) {
                                ui.red.basic.icon.button A {
                                    href = routes.deleteSnippet(it).url
                                    icon.trash()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
