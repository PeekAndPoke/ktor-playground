package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.insights.Chronos
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ktorfx.insights.collectors.RequestCollector
import de.peekandpoke.ktorfx.insights.collectors.ResponseCollector
import io.ktor.http.HttpStatusCode
import kotlin.reflect.KClass

data class InsightsGuiData(
    val ts: Long,
    val date: String,
    val chronos: Chronos,
    val collectors: List<InsightsCollectorData>
) {

    val statusCode: HttpStatusCode? by lazy {
        use(ResponseCollector.Data::class) { status }
    }

    val requestUrl: String by lazy {
        use(RequestCollector.Data::class) { fullUrl } ?: "???"
    }

    val requestMethod: String by lazy {
        use(RequestCollector.Data::class) { method.value } ?: "???"
    }

    val responseTimeMs: String by lazy {
        chronos.totalDurationNs()?.let { "%.2f ms".format(it / 1_000_000.0) } ?: "???"
    }

    fun <T : InsightsCollectorData, R> use(cls: KClass<T>, block: T.() -> R?): R? {

        @Suppress("UNCHECKED_CAST")
        val collector = collectors.firstOrNull { it::class == cls } as T?

        return collector?.block()
    }
}
