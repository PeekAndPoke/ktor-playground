package de.peekandpoke.module.cms

import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ktorfx.broker.getOrPost
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.flashsession.flashSession
import de.peekandpoke.ktorfx.flashsession.success
import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.ktorfx.templating.vm.respond
import de.peekandpoke.module.cms.forms.CmsPageForm
import de.peekandpoke.module.cms.views.CmsMenu
import de.peekandpoke.module.cms.views.editPage
import de.peekandpoke.module.cms.views.index
import de.peekandpoke.module.cms.views.pages
import de.peekandpoke.module.cms.vm.vm
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.vault.New
import de.peekandpoke.ultra.vault.Stored
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.util.pipeline.PipelineContext
import io.ultra.ktor_tools.database
import kotlinx.html.title

fun KontainerBuilder.cmsAdmin() = module(CmsAdminModule)

val CmsAdminModule = module {
    // config
    config("cmsAdminMountPoint", "/cms")

    // application
    singleton(CmsAdminRoutes::class)
    singleton(CmsAdmin::class)

    // database
    singleton(CmsPagesRepository::class)
}

val ApplicationCall.cmsAdminRoutes: CmsAdminRoutes get() = kontainer.get(CmsAdminRoutes::class)

val PipelineContext<Unit, ApplicationCall>.cmsAdminRoutes: CmsAdminRoutes get() = call.cmsAdminRoutes

class CmsAdminRoutes(converter: OutgoingConverter, cmsAdminMountPoint: String) : Routes(converter, cmsAdminMountPoint) {

    val index = route("")

    val pages = route("/pages")

    data class EditPage(val page: Stored<CmsPage>)

    val editPage = route<EditPage>("/pages/{page}/edit")
    fun editPage(page: Stored<CmsPage>) = editPage(EditPage(page))

    val createPage = route("/pages/create")
}

class CmsAdmin(val routes: CmsAdminRoutes) {

    fun Route.mount() {

        get(routes.index) {
            respond {
                index()
            }
        }

        get(routes.pages) {
            respond {
                pages(this@CmsAdmin, database.cmsPages.findAllSorted().toList())
            }
        }

        getOrPost(routes.editPage) { data ->

            respond(call.vm(data.page)) {

                breadCrumbs = listOf(CmsMenu.PAGES)

                pageHead {
                    title { +"CMS Edit Page" }
                }
            }
        }

        getOrPost(routes.createPage) {

            val page = New(CmsPage.empty())
            val form = CmsPageForm.of(page)

            if (form.submit(call)) {

                if (form.isModified) {
                    val saved = database.cmsPages.save(form.result)

                    flashSession.success("Page ${saved.value.name} was created")
                }

                return@getOrPost call.respondRedirect(routes.pages)
            }

            respond {
                editPage(true, form)
            }
        }
    }
}
