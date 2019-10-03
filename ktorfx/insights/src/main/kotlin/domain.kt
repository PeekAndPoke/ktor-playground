package de.peekandpoke.ktorfx.insights

data class InsightsData(
    val ts: Long,
    val date: String,
    val collectors: List<CollectorData>
)

data class CollectorData(val collectorCls: String, val dataCls: String?, val name: String, val data: Any?)

