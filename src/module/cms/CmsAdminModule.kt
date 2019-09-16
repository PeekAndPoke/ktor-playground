package de.peekandpoke.module.cms

import de.peekandpoke.karango.Stored
import de.peekandpoke.karango_ktor.database
import de.peekandpoke.module.cms.forms.PageForm
import de.peekandpoke.module.cms.views.Template
import de.peekandpoke.module.cms.views.editPage
import de.peekandpoke.module.cms.views.index
import de.peekandpoke.module.cms.views.pages
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.feature
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.pipeline.PipelineContext
import io.ultra.ktor_tools.architecture.LinkGenerator
import io.ultra.ktor_tools.architecture.Module
import io.ultra.ktor_tools.bootstrap.success
import io.ultra.ktor_tools.flashSession
import io.ultra.ktor_tools.getOrPost
import io.ultra.ktor_tools.logger.logger
import java.time.ZoneId

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.cmsAdmin() = CmsAdminModule(this)

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class CmsAdminModule(app: Application) : Module(app) {

    val mountPoint = "/cms"

    @Location("")
    class Index

    @Location("/pages")
    class Pages

    @Location("/pages/{page}/edit")
    class EditPage(val page: Stored<CmsPage>)

    @Location("/pages/create")
    class CreatePage

    inner class LinkTo : LinkGenerator(mountPoint, application) {
        fun index() = linkTo(Index())
        fun pages() = linkTo(Pages())
        fun editPage(page: Stored<CmsPage>) = linkTo(EditPage(page))
        fun createPage() = linkTo(CreatePage())
    }

    val linkTo = LinkTo()

    private suspend fun PipelineContext<Unit, ApplicationCall>.respond(status: HttpStatusCode = HttpStatusCode.OK, body: Template.() -> Unit) {
        call.respondHtmlTemplate(Template(linkTo, this), status, body)
    }

    override fun mount(mountPoint: Route) = with(mountPoint) {

        route(this@CmsAdminModule.mountPoint) {

            get<Index> {

                fun printRoutes(routes: List<Route>) {
                    routes.forEach {
                        println(it)
                        println(it.attributes.allKeys)
                        printRoutes(it.children)
                    }
                }

                printRoutes(application.feature(Routing).children)

                respond {
                    index()
                }
            }

            get<Pages> {
                respond {
                    pages(database.cmsPages.findAllSorted().toList())
                }
            }

            getOrPost<EditPage> { data ->

                val form = PageForm(data.page.value.mutator())

                if (form.submit(call)) {

                    if (form.isModified) {
                        val saved = database.cmsPages.save(form.result)
                        logger.info("Updated page in database '${saved.name}'")
                        flashSession.success("Page ${form.result.name} was saved")
                    }

                    return@getOrPost call.respondRedirect(linkTo.pages())
                }

                respond {
                    editPage(data.page.value, form)
                }
            }

            getOrPost<CreatePage> {

                println(ZoneId.getAvailableZoneIds())

                val page = CmsPage.empty()

                val form = PageForm(page.mutator())

                if (form.submit(call)) {

                    if (form.isModified) {
                        val saved = database.cmsPages.save(form.result)
                        logger.info("Updated page in database '${saved.name}'")
                        flashSession.success("Page ${form.result.name} was created")
                    }

                    return@getOrPost call.respondRedirect(linkTo.pages())
                }

                respond {
                    editPage(page, form)
                }

            }
        }
    }
}
