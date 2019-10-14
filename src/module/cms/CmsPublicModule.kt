package de.peekandpoke.module.cms

import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.request.uri
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.database
import kotlinx.html.div
import kotlinx.html.unsafe

fun KontainerBuilder.cmsPublic() = module(CmsPublicModule)

val CmsPublicModule = module {
    singleton(CmsPublic::class)
}

class CmsPublic {

    @KtorExperimentalAPI
    fun Route.mount() {

        get("/*") {

            val path = call.request.uri.trimStart('/')

            val page = database.cmsPages.findBySlug(path) ?: throw NotFoundException("Cms page '$path' not found")

            respond {
                content {
                    div {
                        unsafe {
                            +page.value.markup
                        }
                    }
                }
            }
        }
    }
}
