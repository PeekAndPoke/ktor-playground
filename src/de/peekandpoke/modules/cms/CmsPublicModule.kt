package de.peekandpoke.modules.cms

import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.modules.cms.db.cmsPages
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.request.uri
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.pipeline.PipelineContext
import io.ultra.ktor_tools.database
import kotlinx.html.title

fun KontainerBuilder.cmsPublic() = module(CmsPublicModule)

val CmsPublicModule = module {
    singleton(CmsPublic::class)
}

class CmsPublic() {

    @KtorExperimentalAPI
    fun Route.mount() {

        get {
            servePage("")
        }

        get("/{...}") {

            val slug = call.request.uri.trimStart('/')

            servePage(slug)
        }
    }

    @KtorExperimentalAPI
    private suspend fun PipelineContext<Unit, ApplicationCall>.servePage(slug: String) {

        val page = database.cmsPages.findBySlug(slug) ?: throw NotFoundException("Cms page '$slug' not found")

        val ctx = RenderCtx(cms, call)

        respond {

            pageHead {
                title { +page.value.name }
            }

            content {
                page.value.layout.apply { render(ctx) }
            }
        }
    }
}
