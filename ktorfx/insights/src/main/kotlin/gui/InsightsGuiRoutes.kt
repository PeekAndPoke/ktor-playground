package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes

class InsightsGuiRoutes(converter: OutgoingConverter) : Routes(converter) {

    data class GetBar(val bucket: String, val filename: String)

    val bar = route(GetBar::class, "/_/insights/bar/{bucket}/{filename}")
}
