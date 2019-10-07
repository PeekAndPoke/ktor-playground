package de.peekandpoke.ktorfx.insights

import java.time.LocalDateTime

data class InsightsData(
    val ts: LocalDateTime,
    val date: String,
    val chronos: Chronos,
    val collectors: List<CollectorData>
)

data class CollectorData(val cls: String, val data: Map<*, *>)

