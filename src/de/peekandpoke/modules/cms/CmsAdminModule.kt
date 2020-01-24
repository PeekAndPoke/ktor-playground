package de.peekandpoke.modules.cms

import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ktorfx.broker.getOrPost
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.flashsession.flashSession
import de.peekandpoke.ktorfx.flashsession.success
import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.ktorfx.templating.vm.respond
import de.peekandpoke.modules.cms.db.cmsPages
import de.peekandpoke.modules.cms.db.cmsSnippets
import de.peekandpoke.modules.cms.domain.CmsPage
import de.peekandpoke.modules.cms.domain.CmsPageForm
import de.peekandpoke.modules.cms.domain.CmsSnippet
import de.peekandpoke.modules.cms.domain.CmsSnippetForm
import de.peekandpoke.modules.cms.views.*
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
}

val ApplicationCall.cmsAdminRoutes: CmsAdminRoutes get() = kontainer.get(CmsAdminRoutes::class)

val PipelineContext<Unit, ApplicationCall>.cmsAdminRoutes: CmsAdminRoutes get() = call.cmsAdminRoutes

class CmsAdminRoutes(converter: OutgoingConverter, cmsAdminMountPoint: String) : Routes(converter, cmsAdminMountPoint) {

    val index = route("")

    ////  PAGES  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    val pages = route("/pages")

    val createPage = route("/pages/create")

    data class EditPage(val page: Stored<CmsPage>)

    val editPage = route<EditPage>("/pages/{page}/edit")

    fun editPage(page: Stored<CmsPage>) = editPage(EditPage(page))

    ////  PAGES  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    val snippets = route("/snippets")

    val createSnippet = route("/snippets/create")

    data class EditSnippet(val snippet: Stored<CmsSnippet>)

    val editSnippet = route<EditSnippet>("/snippets/{snippet}/edit")

    fun editSnippet(snippet: Stored<CmsSnippet>) = editSnippet(EditSnippet(snippet))
}

class CmsAdmin(val routes: CmsAdminRoutes) {

    fun Route.mount() {

        get(routes.index) {
            respond {
                index(routes)
            }
        }

        get(routes.pages) {
            respond {
                pages(routes, database.cmsPages.findAllSorted().toList())
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
                createPage(form)
            }
        }

        get(routes.snippets) {
            respond {
                snippets(routes, database.cmsSnippets.findAllSorted().toList())
            }
        }

        getOrPost(routes.createSnippet) {

            val page = New(CmsSnippet())
            val form = CmsSnippetForm.of(page)

            if (form.submit(call)) {

                if (form.isModified) {
                    val saved = database.cmsSnippets.save(form.result)

                    flashSession.success("Snippet ${saved.value.name} was created")
                }

                return@getOrPost call.respondRedirect(routes.snippets)
            }

            respond {
                createSnippet(form)
            }
        }

        getOrPost(routes.editSnippet) { data ->

            respond(call.vm(data.snippet)) {

                breadCrumbs = listOf(CmsMenu.PAGES)

                pageHead {
                    title { +"CMS Edit Snippet" }
                }
            }
        }
    }
}
