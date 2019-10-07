package de.peekandpoke.module.semanticui

import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.module.semanticui.views.buttons
import de.peekandpoke.module.semanticui.views.icons
import de.peekandpoke.module.semanticui.views.index
import de.peekandpoke.module.semanticui.views.playground
import de.peekandpoke.ultra.kontainer.module
import io.ktor.routing.Route
import io.ktor.routing.get

val SemanticUiModule = module {

    config("semanticUiMountPoint", "/semantic-ui")

    singleton(SemanticUiRoutes::class)
    singleton(SemanticUi::class)
}

class SemanticUiRoutes(converter: OutgoingConverter, semanticUiMountPoint: String) : Routes(converter, semanticUiMountPoint) {

    val index = route("")
    val buttons = route("/buttons")
    val icons = route("/icons")
    val playground = route("/playground")
}

class SemanticUi(val routes: SemanticUiRoutes) {


    fun mount(route: Route) = with(route) {

        get(routes.index) {
            respond { index() }
        }

        get(routes.buttons) {
            respond { buttons() }
        }

        get(routes.icons) {
            respond { icons() }
        }

        get(routes.playground) {
            respond { playground() }
        }
    }
}
