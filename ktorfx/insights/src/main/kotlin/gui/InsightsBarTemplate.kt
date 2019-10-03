package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.semanticui.ui
import io.ktor.html.PlaceholderList
import io.ktor.html.each
import io.ktor.html.insert
import kotlinx.html.FlowContent
import kotlinx.html.TagConsumer
import kotlinx.html.div

class InsightsBarTemplate(private val consumer: TagConsumer<Appendable>) {

    val status = PlaceholderList<FlowContent, FlowContent>()

    val right = PlaceholderList<FlowContent, FlowContent>()

    fun apply(guiData: InsightsGuiData) = apply {
        guiData.collectors.forEach {
            it.renderBar(this)
        }
    }

    fun render() = consumer.div(classes = "insights-bar") {

        ui.horizontal.list {

            each(status) { insert(it) }

            each(right) { insert(it) }
        }
    }
}
