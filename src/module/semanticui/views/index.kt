package de.peekandpoke.module.semanticui.views

import de.peekandpoke.module.semanticui.Template
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal fun Template.index() {

    content {
        +"INDEX page"
    }

}
