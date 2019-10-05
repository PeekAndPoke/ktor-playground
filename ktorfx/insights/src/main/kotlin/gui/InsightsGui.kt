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

    fun mount(route: Route) = with(route) {

        get(routes.bar) { bucketAndFile ->

            val guiData = loadData(application, kontainer, bucketAndFile.bucket, bucketAndFile.filename)

            call.respondBytes(ContentType.Text.Html, HttpStatusCode.OK) {

                val content = InsightsBarTemplate(bucketAndFile, routes, guiData, StringWriter().appendHTML()).render()

                content.toString().toByteArray()
            }
        }

        get(routes.details) { bucketAndFile ->

            val guiData = loadData(application, kontainer, bucketAndFile.bucket, bucketAndFile.filename)

            val template = InsightsDetailsTemplate(kontainer.get(WebResources::class), guiData)

            call.respondHtmlTemplate(template, HttpStatusCode.OK) {}
        }
    }

    /**
     *  TODO: move to [Insights]
     */
    private fun loadData(application: Application, kontainer: Kontainer, bucket: String, filename: String): InsightsGuiData {

        val storage = kontainer.get(InsightsRepository::class)
        val mapper = kontainer.get(InsightsMapper::class)

        val recordBytes = storage.get(bucket).getFile(filename).getContentBytes()
        val recordString = String(recordBytes)

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

        return InsightsGuiData(insightsData.ts, insightsData.date, insightsData.chronos, collectors)
    }
}
