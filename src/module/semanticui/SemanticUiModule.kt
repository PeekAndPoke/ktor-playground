package de.peekandpoke.module.semanticui

import de.peekandpoke.module.semanticui.views.buttons
import de.peekandpoke.module.semanticui.views.index
import de.peekandpoke.module.semanticui.views.playground
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.pipeline.PipelineContext
import io.ultra.ktor_tools.architecture.LinkGenerator

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.semanticUi(): SemanticUiModule {

    @KtorExperimentalLocationsAPI
    lateinit var module: SemanticUiModule

    routing {
        route("/semantic-ui") {
            module = SemanticUiModule(this)
        }
    }

    return module
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class SemanticUiModule(val mountPoint: Route) {

    @Location("")
    internal class Index

    @Location("/buttons")
    internal class Buttons

    @Location("/playground")
    internal class Playground

    inner class LinkTo : LinkGenerator(mountPoint) {
        fun index() = linkTo(Index())
        fun buttons() = linkTo(Buttons())
        fun playground() = linkTo(Playground())
    }

    private val linkTo = LinkTo()

    private suspend fun PipelineContext<Unit, ApplicationCall>.respond(status: HttpStatusCode = HttpStatusCode.OK, body: Template.() -> Unit) {
        call.respondHtmlTemplate(Template(linkTo, call), status, body)
    }

    init {
        with(mountPoint) {

            get<Index> {
                respond { index() }
            }

            get<Buttons> {
                respond { buttons() }
            }

            get<Playground> {
                respond { playground() }
            }
        }
    }

}
