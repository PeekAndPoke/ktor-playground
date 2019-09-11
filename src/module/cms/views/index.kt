package de.peekandpoke.module.cms.views

import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import kotlinx.html.h1

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal fun Template.index() {

    activeMenu = MenuEntries.HOME

    content {
        h1 { +"Welcome to the CMS!" }
    }
}
