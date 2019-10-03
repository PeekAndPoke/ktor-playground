package de.peekandpoke.ktorfx.insights.gui

import com.fasterxml.jackson.module.kotlin.readValue
import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsData
import de.peekandpoke.ktorfx.insights.InsightsMapper
import de.peekandpoke.ktorfx.insights.InsightsRepository
import de.peekandpoke.ultra.kontainer.Kontainer
import io.ktor.application.Application
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondBytes
import io.ktor.routing.Route
import kotlinx.html.stream.appendHTML
import java.io.StringWriter
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.primaryConstructor

class InsightsGui(private val routes: InsightsGuiRoutes) {

    fun mount(route: Route) = with(route) {

        get(routes.bar) { data ->

            val guiData = loadData(application, kontainer, data.bucket, data.filename)

            call.respondBytes(ContentType.Text.Html, HttpStatusCode.OK) {

                val content = InsightsBarTemplate(StringWriter().appendHTML())
                    .apply(guiData)
                    .render()

                content.toString().toByteArray()
            }


//            call.respondHtml {
//                body {
//                    div(classes = "insights-bar") {
//
//                        style = "position: fixed; bottom: 0; border: 1px solid grey; background-color: red; z-index: 10000; width: 100%;"
//
//                        div {
//                            +data.id
//                        }
//
//                        div {
//                            +"200 OK"
//                        }
//                    }
//                }
//            }
        }
    }

    private fun loadData(application: Application, kontainer: Kontainer, bucket: String, filename: String): InsightsGuiData {

        val storage = kontainer.get(InsightsRepository::class)
        val mapper = kontainer.get(InsightsMapper::class)

        val recordBytes = storage.get(bucket).getFile(filename).getContentBytes()
        val recordString = String(recordBytes)

        val insightsData = mapper.readValue<InsightsData>(recordString)

        val collectors = insightsData.collectors
            .map {
                try {
                    val collectorCls = Class.forName(it.collectorCls).kotlin

                    if (!collectorCls.allSuperclasses.contains(InsightsCollector::class)) {
                        return@map null
                    }

                    val dataCls = Class.forName(it.dataCls)

                    val dataMapped = mapper.convertValue(it.data, dataCls)

                    return@map collectorCls.primaryConstructor!!.call(dataMapped) as InsightsCollector

                } catch (e: Throwable) {
                    application.log.warn(e.message)
                    return@map null
                }
            }
            .filterNotNull()


        return InsightsGuiData(insightsData.ts, insightsData.date, collectors)
    }
}
