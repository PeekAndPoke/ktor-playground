package de.peekandpoke.module.cms.views

import de.peekandpoke.ktorfx.templating.SimpleTemplate
import kotlinx.html.h1
import kotlinx.html.title

internal fun SimpleTemplate.index() {

    breadCrumbs = listOf(CmsMenu.INDEX)

    pageTitle {
        title { +"CMS" }
    }

    content {
        h1 { +"Welcome to the CMS!" }
    }
}
