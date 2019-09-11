package de.peekandpoke.module.cms

import de.peekandpoke.karango_ktor.database
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.request.uri
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.architecture.Module

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.cmsPublic() = CmsPublicModule(this)

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class CmsPublicModule(app: Application) : Module(app) {

    override fun mount(mountPoint: Route) = with(mountPoint) {

        get("/*") {

            val path = call.request.uri.trimStart('/')

            val page = database.cmsPages.findBySlug(path) ?: throw NotFoundException("Cms page '$path' not found")

            call.respondText(ContentType.Text.Html, HttpStatusCode.OK) {
                page.markup
            }
        }
    }
}
