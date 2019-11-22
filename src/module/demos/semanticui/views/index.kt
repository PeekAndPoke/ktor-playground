package de.peekandpoke.module.semanticui.views

import de.peekandpoke.ktorfx.templating.SimpleTemplate
import kotlinx.html.title

internal fun SimpleTemplate.index() {

    breadCrumbs = listOf(SemanticUiMenu.Index)

    pageTitle {
        title { +"SemanticUI Showcase" }
    }

    content {
        +"Semantic UI showcase"
    }
}
