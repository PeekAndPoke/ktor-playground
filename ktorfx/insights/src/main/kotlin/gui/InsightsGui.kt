package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.broker.TypedRouteRenderer
import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.insights.Insights
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondBytes
import io.ktor.routing.Route

class InsightsGui(
    private val routes: InsightsGuiRoutes,
    private val insights: Insights
) {

    fun Route.mount() {

        get(routes.bar) { bucketAndFile ->

            val guiData = insights.loadGuiData(application, bucketAndFile.bucket, bucketAndFile.filename)

            if (guiData != null) {
                call.respondBytes(ContentType.Text.Html, HttpStatusCode.OK) {
                    InsightsBarTemplate(
                        bucketAndFile,
                        routes,
                        guiData,
                        kontainer.get(TypedRouteRenderer::class)
                    ).render().toString().toByteArray()
                }
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get(routes.details) { bucketAndFile ->

            val guiData = insights.loadGuiData(application, bucketAndFile.bucket, bucketAndFile.filename)

            if (guiData != null) {
                val template = kontainer.get(InsightsGuiTemplate::class)

                call.respondHtmlTemplate(template, HttpStatusCode.OK) {
                    render(guiData)
                }
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}
