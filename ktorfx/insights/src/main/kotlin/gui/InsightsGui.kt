package de.peekandpoke.ktorfx.insights.gui

import com.fasterxml.jackson.module.kotlin.readValue
import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.insights.*
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ultra.kontainer.Kontainer
import io.ktor.application.Application
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondBytes
import io.ktor.routing.Route
import kotlinx.html.stream.appendHTML
import java.io.StringWriter
import kotlin.reflect.full.allSuperclasses

class InsightsGui(private val routes: InsightsGuiRoutes) {

    fun Route.mount() {

        get(routes.bar) { bucketAndFile ->

            val guiData = loadData(application, kontainer, bucketAndFile.bucket, bucketAndFile.filename)

            call.respondBytes(ContentType.Text.Html, HttpStatusCode.OK) {

                val content = InsightsBarTemplate(bucketAndFile, routes, guiData, StringWriter().appendHTML()).render()

                content.toString().toByteArray()
            }
        }

        get(routes.details) { bucketAndFile ->

            val guiData = loadData(application, kontainer, bucketAndFile.bucket, bucketAndFile.filename)

            // TODO: avoid getting the service manually
            val template = InsightsGuiTemplate(
                routes,
                kontainer.get(WebResources::class),
                kontainer.get(InsightsMapper::class),
                guiData
            )

            call.respondHtmlTemplate(template, HttpStatusCode.OK) {}
        }
    }

    /**
     *  TODO: move to [Insights]
     */
    private fun loadData(application: Application, kontainer: Kontainer, bucket: String, filename: String): InsightsGuiData {

        val storage = kontainer.get(InsightsRepository::class)
        val mapper = kontainer.get(InsightsMapper::class)

        // get all newest files
        val files = storage.get(bucket).listNewest()
        // get the actual file
        val file = storage.get(bucket).getFile(filename)
        val fileIdx = files.indexOf(file)
        // get the previous and next file
        val nextFile = if (fileIdx > 0) files[fileIdx - 1] else null
        val previousFile = if (fileIdx < files.size - 1) files[fileIdx + 1] else null

        // read file contents
        val recordString = String(file.getContentBytes())
        val insightsData = mapper.readValue<InsightsData>(recordString)

        val collectors = insightsData.collectors
            .map {
                try {
                    val cls = Class.forName(it.cls).kotlin

                    if (!cls.allSuperclasses.contains(InsightsCollectorData::class)) {
                        return@map null
                    }

                    return@map mapper.convertValue(it.data, cls.java) as InsightsCollectorData

                } catch (e: Throwable) {
                    application.log.warn(e.message)
                    return@map null
                }
            }
            .filterNotNull()

        return InsightsGuiData(
            insightsData.ts,
            insightsData.date,
            insightsData.chronos,
            collectors,
            nextFile,
            previousFile
        )
    }
}