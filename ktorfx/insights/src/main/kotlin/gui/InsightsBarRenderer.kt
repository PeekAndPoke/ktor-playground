package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.insights.Insights
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.style

class InsightsBarRenderer(private val insights: Insights) {

    fun render(tag: FlowContent) {

        tag.div(classes = "insights-bar-placeholder") {
            attributes["data-insights-bucket"] = insights.bucket
            attributes["data-insights-filename"] = insights.filename
            style = "display: none"
//            style = "position: fixed; bottom: 0; border: 1px solid grey; background-color: red; z-index: 10000; width: 100%;"
//
//            +insights.filename
        }
    }
}
