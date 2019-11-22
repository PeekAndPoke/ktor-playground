package de.peekandpoke.module.cms

import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.module.cms.elements.Cms
import de.peekandpoke.module.cms.elements.CmsElement
import de.peekandpoke.module.cms.elements.CmsLayout
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.request.uri
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.database
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h3

fun KontainerBuilder.cmsPublic() = module(CmsPublicModule)

val CmsPublicModule = module {
    singleton(CmsPublic::class)
}

class LandingPageLayout(private val cms: Cms) : CmsLayout<LandingPageLayout.Data>(Data::class) {

    data class Data(val people: List<GreetElement.Data>) : CmsLayout.Data

    override fun FlowContent.render(data: Data) {
        div {
            h1 { +"Landing Page" }

            cms.apply { render(data.people) }
        }
    }
}

class GreetAllElement(private val cms: Cms) : CmsElement<GreetAllElement.Data>(Data::class) {

    data class Data(val people: List<GreetElement.Data>) : CmsElement.Data

    override fun FlowContent.render(data: Data) {

        div {

            h3 { +"Greet all" }

            div {
                +CmsElement.Data.childTypes.toString()
            }

            data.people.forEach {
                cms.apply { render(it) }
            }
        }
    }
}

class GreetElement : CmsElement<GreetElement.Data>(Data::class) {

    data class Data(val name: String) : CmsElement.Data

    override fun FlowContent.render(data: Data) {
        div {
            +"Hello ${data.name}!"
        }
    }
}

class CmsPublic {

    @KtorExperimentalAPI
    fun Route.mount() {

        get("/_cms_test_") {

            val data = GreetAllElement.Data(
                listOf(
                    GreetElement.Data("Greta"),
                    GreetElement.Data("Nadin")
                )
            )

            respond {
                content {
                    cms.apply { render(data) }
                }
            }
        }

        get("/*") {

            val path = call.request.uri.trimStart('/')

            val page = database.cmsPages.findBySlug(path) ?: throw NotFoundException("Cms page '$path' not found")

            println(page)

            respond {
                content {
                    page.value.data?.let {
                        cms.apply { render(it) }
                    }
                }
            }
        }
    }
}
