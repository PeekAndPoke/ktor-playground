package de.peekandpoke.module.cms

import de.peekandpoke.karango_ktor.database
import de.peekandpoke.module.cms.forms.CmsPageForm
import de.peekandpoke.module.cms.views.Template
import de.peekandpoke.module.cms.views.editPage
import de.peekandpoke.module.cms.views.index
import de.peekandpoke.module.cms.views.pages
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.vault.New
import de.peekandpoke.ultra.vault.Stored
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.util.pipeline.PipelineContext
import io.ultra.ktor_tools.ParamConversionService
import io.ultra.ktor_tools.Routes
import io.ultra.ktor_tools.bootstrap.success
import io.ultra.ktor_tools.flashSession
import io.ultra.ktor_tools.getOrPost
import io.ultra.ktor_tools.logger.logger

val CmsAdminModule = module {
    config("cmsAdminMountPoint", "/cms")

    singleton<CmsAdminRoutes>()
    singleton<CmsAdmin>()
}

class CmsAdminRoutes(converter: ParamConversionService, cmsAdminMountPoint: String) : Routes(converter, cmsAdminMountPoint) {

    val index = route("")

    val pages = route("/pages")

    data class EditPage(val page: Stored<CmsPage>)

    val editPage = route<EditPage>("/pages/{page}/edit")
    fun editPage(page: Stored<CmsPage>) = editPage(EditPage(page))

    val createPage = route("/pages/create")
}

class CmsAdmin(val routes: CmsAdminRoutes) {

    private suspend fun PipelineContext<Unit, ApplicationCall>.respond(status: HttpStatusCode = HttpStatusCode.OK, body: Template.() -> Unit) {
        call.respondHtmlTemplate(Template(routes, this), status, body)
    }

    fun mount(route: Route) = with(route) {

        get(routes.index) {

            fun printRoutes(routes: List<Route>) {
                routes.forEach {
                    println(it)
                    println(it.attributes.allKeys)
                    printRoutes(it.children)
                }
            }

            respond {
                index()
            }
        }

        get(routes.pages) {
            respond {
                pages(database.cmsPages.findAllSorted().toList())
            }
        }

        getOrPost(routes.editPage) { data ->

            val form = CmsPageForm.of(data.page)

            if (form.submit(call)) {
                if (form.isModified) {
                    val saved = database.cmsPages.save(form.result)
                    flashSession.success("Page ${saved.value.name} was saved")
                }

                return@getOrPost call.respondRedirect(routes.pages)
            }

            respond { editPage(false, form) }
        }

        getOrPost(routes.createPage) {

            val page = New(CmsPage.empty())

            val form = CmsPageForm.of(page)

            if (form.submit(call)) {

                if (form.isModified) {
                    val saved = database.cmsPages.save(form.result)
                    logger.info("Updated page in database '${saved.value.name}'")
                    flashSession.success("Page ${form.result.value.name} was created")
                }

                return@getOrPost call.respondRedirect(routes.pages)
            }

            respond {
                editPage(true, form)
            }

        }

    }
}
