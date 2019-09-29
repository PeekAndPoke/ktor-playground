package de.peekandpoke.module.cms.views

import de.peekandpoke.ktorfx.templating.SimpleTemplate
import kotlinx.html.h1

internal fun SimpleTemplate.index() {

    breadCrumbs = listOf(MenuEntries.HOME)

    content {
        h1 { +"Welcome to the CMS!" }
    }
}
