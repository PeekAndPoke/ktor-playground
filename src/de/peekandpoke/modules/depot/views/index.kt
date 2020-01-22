package de.peekandpoke.modules.depot.views

import de.peekandpoke.ktorfx.templating.SimpleTemplate
import kotlinx.html.h1
import kotlinx.html.title

internal fun SimpleTemplate.index() {

    breadCrumbs = listOf(DepotMenu.INDEX)

    pageHead {
        title { +"Depot" }
    }

    content {
        h1 { +"Welcome to the File Depot!" }
    }
}
