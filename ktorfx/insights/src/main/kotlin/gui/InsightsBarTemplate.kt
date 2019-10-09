package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import io.ktor.html.PlaceholderList
import io.ktor.html.each
import io.ktor.html.insert
import io.ktor.http.isSuccess
import kotlinx.html.*

class InsightsBarTemplate(
    private val data: InsightsGuiRoutes.BucketAndFile,
    private val routes: InsightsGuiRoutes,
    private val guiData: InsightsGuiData,
    private val consumer: TagConsumer<Appendable>
) {

    val status = PlaceholderList<FlowContent, FlowContent>()

    val left = PlaceholderList<FlowContent, FlowContent>()

    val right = PlaceholderList<FlowContent, FlowContent>()

    init {

        status {

            ui.item {
                val statusCode = guiData.statusCode

                a(href = routes.details(data), target = "_blank") {
                    when {
                        statusCode == null -> ui.grey.label { +"???" }

                        statusCode.isSuccess() -> ui.green.label { +(statusCode.value).toString() }

                        else -> ui.red.label { +(statusCode.value).toString() }
                    }
                }
            }
        }

        left {

            ui.item {
                title = "Processing time"

                val durationNs = guiData.chronos.totalDurationNs()

                if (durationNs == null) {
                    icon.grey.clock()
                    +"???"
                } else {

                    val durationMillis: Double = durationNs / 1_000_000.0

                    when {
                        durationMillis > 300 -> icon.red.clock()
                        durationMillis > 150 -> icon.yellow.clock()
                        durationMillis > 75 -> icon.olive.clock()
                        else -> icon.green.clock()
                    }

                    +"%.2f ms".format(durationMillis)
                }
            }
        }

        guiData.collectors.forEach {
            it.renderBar(this)
        }
    }

    fun render() = consumer.div(classes = "insights-bar") {
        id = "insights-bar"

        ui.attached.inverted.segment {

            ui.inverted.horizontal.divided.list {

                each(status) { insert(it) }
                each(left) { insert(it) }
                each(right) { insert(it) }

            }

            span {
                id = "close-insights-bar"
                style = "float: right; margin-top: 6px;"
                icon.inverted.window_close_outline()
            }
        }
    }
}
