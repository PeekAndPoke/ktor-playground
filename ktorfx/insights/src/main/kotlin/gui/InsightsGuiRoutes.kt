package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ultra.depot.DepotFile

class InsightsGuiRoutes(converter: OutgoingConverter) : Routes(converter, "/_") {

    data class BucketAndFile(val bucket: String, val filename: String)

    val bar = route(BucketAndFile::class, "/insights/bar/{bucket}/{filename}")

    val details = route(BucketAndFile::class, "/insights/details/{bucket}/{filename}/show")

    fun details(bucket: String, filename: String) = details.bind(BucketAndFile(bucket, filename))

    fun details(file: DepotFile) = details(file.bucket.name, file.name)
}
