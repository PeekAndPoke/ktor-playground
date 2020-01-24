package de.peekandpoke.modules.cms.views

import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.modules.cms.CmsAdminRoutes
import kotlinx.html.title

internal fun SimpleTemplate.index(routes: CmsAdminRoutes) {

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
    }
}
