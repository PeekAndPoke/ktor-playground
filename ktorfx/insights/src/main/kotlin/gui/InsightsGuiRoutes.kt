package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes

class InsightsGuiRoutes(converter: OutgoingConverter) : Routes(converter) {

    data class BucketAndFile(val bucket: String, val filename: String)

    val bar = route(BucketAndFile::class, "/_/insights/bar/{bucket}/{filename}")

    val details = route(BucketAndFile::class, "/_/insights/details/{bucket}/{filename}")
}
