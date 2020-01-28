package de.peekandpoke.modules.cms.views

import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.modules.cms.Cms
import de.peekandpoke.modules.cms.CmsAdminRoutes
import de.peekandpoke.modules.cms.domain.CmsPage
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.*

internal fun SimpleTemplate.pages(routes: CmsAdminRoutes, pages: List<Stored<CmsPage>>, cms: Cms) {

    breadCrumbs = listOf(CmsMenu.PAGES)

    pageHead {
        title { +"CMS Pages" }
    }

    content {
        ui.dividing.header H1 {
            +"Pages"
        }

        ui.attached.segment {
            ui.grid {

                ui.twelve.wide.column {
                    ui.form Form {
                        method = FormMethod.get

                        ui.field {
                            textInput(name = "search") {
                                placeholder = "Search"
                            }
                        }
                    }
                }

                ui.four.wide.right.aligned.column {
                    ui.basic.primary.button A {
                        href = routes.createPage
                        +"Create Page"
                    }
                }
            }
        }

        ui.celled.table Table {
            thead {
                tr {
                    th { +"Id" }
                    th { +"Name" }
                    th { +"Uri" }
                    th { +"Updated at" }
                    th { +"Last edit by" }
                    th { +"" }
                }
            }

            tbody {
                pages.forEach {
                    tr {
                        td {
                            a(href = routes.editPage(it).url) { +it._id }
                        }
                        td {
                            a(href = routes.editPage(it).url) { +it.value.name }
                        }
                        td {
                            a(href = routes.editPage(it).url) { +it.value.uri }
                        }
                        td {
                            +(it._meta?.ts?.updatedAt?.toString() ?: "n/a")
                        }
                        td {
                            +(it._meta?.user?.userId ?: "n/a")
                        }
                        td {
                            if (cms.canDelete(it)) {
                                ui.red.inverted.icon.button A {
                                    href = routes.deletePage(it).url
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
