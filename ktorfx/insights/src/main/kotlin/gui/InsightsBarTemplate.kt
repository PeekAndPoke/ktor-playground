package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import io.ktor.html.PlaceholderList
import io.ktor.html.each
import io.ktor.html.insert
import io.ktor.http.isSuccess
import kotlinx.html.FlowContent
import kotlinx.html.TagConsumer
import kotlinx.html.div

class InsightsBarTemplate(private val guiData: InsightsGuiData, private val consumer: TagConsumer<Appendable>) {

    val status = PlaceholderList<FlowContent, FlowContent>()

    val left = PlaceholderList<FlowContent, FlowContent>()

    val right = PlaceholderList<FlowContent, FlowContent>()

    init {

        status {

            ui.item {
                val statusCode = guiData.statusCode

                when {
                    statusCode == null -> ui.grey.label { +"???" }

                    statusCode.isSuccess() -> ui.green.label { +(statusCode.value).toString() }

                    else -> ui.red.label { +(statusCode.value).toString() }
                }
            }
        }

        left {
            ui.item {
                icon.clock()

                val durationNs = guiData.chronos.totalDurationNs()

                val duration = when {
                    durationNs != null -> "%.2f".format(durationNs / 1_000_000.0)
                    else -> "???"
                }

                +("$duration ms")
            }
        }

        guiData.collectors.forEach {
            it.renderBar(this)
        }
    }

    fun render() = consumer.div(classes = "insights-bar") {

        ui.inverted.segment {
            ui.inverted.horizontal.divided.list {

                each(status) { insert(it) }

                each(left) { insert(it) }

                each(right) { insert(it) }
            }
        }
    }
}
