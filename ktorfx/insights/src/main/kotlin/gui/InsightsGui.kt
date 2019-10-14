package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.insights.Insights
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondBytes
import io.ktor.routing.Route
import kotlinx.html.stream.appendHTML
import java.io.StringWriter

class InsightsGui(
    private val routes: InsightsGuiRoutes,
    private val insights: Insights
) {

    fun Route.mount() {

        get(routes.bar) { bucketAndFile ->

            val guiData = insights.loadGuiData(application, bucketAndFile.bucket, bucketAndFile.filename)

            call.respondBytes(ContentType.Text.Html, HttpStatusCode.OK) {

                val content = InsightsBarTemplate(bucketAndFile, routes, guiData, StringWriter().appendHTML()).render()

                content.toString().toByteArray()
            }
        }

        get(routes.details) { bucketAndFile ->

            val guiData = insights.loadGuiData(application, bucketAndFile.bucket, bucketAndFile.filename)

            val template = kontainer.get(InsightsGuiTemplate::class)

            call.respondHtmlTemplate(template, HttpStatusCode.OK) {
                render(guiData)
            }
        }
    }
}
