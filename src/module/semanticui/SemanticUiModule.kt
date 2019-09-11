package de.peekandpoke.module.semanticui

import de.peekandpoke.module.semanticui.views.buttons
import de.peekandpoke.module.semanticui.views.icons
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
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.pipeline.PipelineContext
import io.ultra.ktor_tools.architecture.LinkGenerator
import io.ultra.ktor_tools.architecture.Module

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.semanticUi() = SemanticUiModule(this)

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class SemanticUiModule(app: Application) : Module(app) {

    val mountPoint = "/semantic-ui"

    @Location("")
    class Index

    @Location("/buttons")
    class Buttons

    @Location("/icons")
    class Icons

    @Location("/playground")
    class Playground

    inner class LinkTo : LinkGenerator(mountPoint, application) {
        fun index() = linkTo(Index())
        fun buttons() = linkTo(Buttons())
        fun icons() = linkTo(Icons())
        fun playground() = linkTo(Playground())
    }

    val linkTo = LinkTo()

    private suspend fun PipelineContext<Unit, ApplicationCall>.respond(status: HttpStatusCode = HttpStatusCode.OK, body: Template.() -> Unit) {
        call.respondHtmlTemplate(Template(linkTo, call), status, body)
    }

    override fun mount(mountPoint: Route) {

        mountPoint.route(this.mountPoint) {

            get<Index> {
                respond { index() }
            }

            get<Buttons> {
                respond { buttons() }
            }

            get<Icons> {
                respond { icons() }
            }

            get<Playground> {
                respond { playground() }
            }
        }
    }

}
