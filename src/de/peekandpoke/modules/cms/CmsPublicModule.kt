package de.peekandpoke.modules.cms

import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.logging.Log
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

class CmsPublic(
    private val log: Log
) {

    @KtorExperimentalAPI
    fun Route.mount() {

        get("") {
            log.debug("Serving home page")

            servePage("")
        }

        get("/*") {

            val slug = call.request.uri.trimStart('/')

            log.debug("Serving page '$slug'")

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
