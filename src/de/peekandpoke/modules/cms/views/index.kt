package de.peekandpoke.modules.cms.views

import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.modules.cms.CmsAdminRoutes
import de.peekandpoke.modules.cms.CmsValidity
import kotlinx.html.title

internal fun SimpleTemplate.index(routes: CmsAdminRoutes, validity: CmsValidity) {

    breadCrumbs = listOf(CmsMenu.INDEX)

    pageHead {
        title { +"CMS" }
    }

    content {

        ui.divided.header H1 { +"Welcome to the CMS!" }

        ui.massive.inverted.violet.button A {
            href = routes.pages
            +"Pages"
        }

        ui.massive.inverted.violet.button A {
            href = routes.snippets
            +"Snippets"
        }

        ui.divider {}


        when {
            validity.isValid -> {
                ui.green.segment {
                    ui.header H3 { +"Cms Integrity OK" }

                    +"All is well"
                }
            }

            else -> {
                ui.red.segment {
                    ui.header H3 { +"Cms Integrity NOT OK" }

                    ui.relaxed.divided.list {
                        validity.errors.forEach {
                            ui.item {
                                icon.big.red.exclamation_triangle()
                                ui.content { +it }
                            }
                        }
                    }
                }
            }
        }
    }
}
