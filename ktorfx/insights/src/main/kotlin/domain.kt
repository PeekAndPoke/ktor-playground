package de.peekandpoke.ktorfx.insights

data class InsightsData(
    val ts: Long,
    val date: String,
    val chronos: Chronos,
    val collectors: List<CollectorData>
)

data class CollectorData(val cls: String, val data: Map<*, *>)

