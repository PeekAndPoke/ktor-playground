package de.peekandpoke.ktorfx.insights

import com.fasterxml.jackson.module.kotlin.convertValue
import de.peekandpoke.ultra.common.Lookup
import io.ktor.application.ApplicationCall
import io.ktor.request.uri
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

class Chronos {

    var startNs: Long? = null
    var endNs: Long? = null

    fun start() {
        startNs = System.nanoTime()
    }

    fun end() {
        endNs = System.nanoTime()
    }

    fun totalDurationNs(): Long? {
        if (startNs == null || endNs == null) {
            return null
        }

        return endNs!! - startNs!!
    }
}

class Insights(
    private val collectors: Lookup<InsightsCollector>,
    private val repository: InsightsRepository,
    private val mapper: InsightsMapper
) {
    private val date = LocalDate.now()
    private val dateTime = LocalDateTime.now()
    private val ts = Instant.now()

    private val chronos: Chronos = Chronos().apply { start() }

    // TODO: make injectable
    private val filter = listOf<(ApplicationCall) -> Boolean>(
        { it.request.uri.contains("favicon.ico") },
        { it.request.uri.startsWith("/_/") }
    )

    val bucket = "records-${date}"
    val filename = "$dateTime.$ts.json"

    fun <T : InsightsCollector, R> use(cls: KClass<T>, block: T.() -> R?): R? {
        return collectors.get(cls)?.block()
    }

    fun finish(call: ApplicationCall) {

        chronos.end()

        // do not record if any of the filters match
        if (filter.any { it(call) }) {
            return
        }

        // finish all collectors
        val entries = collectors.all().map { it.finish(call) }

        val data = InsightsData(
            dateTime,
            dateTime.toString(),
            chronos,
            entries.map {
                CollectorData(it::class.jvmName, mapper.convertValue(it))
            }
        )

        repository
            .createBucket(bucket)
            .putFile(
                filename,
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data)
            )
    }
}

