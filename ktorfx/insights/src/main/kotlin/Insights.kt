package de.peekandpoke.ktorfx.insights

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.readValue
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiData
import de.peekandpoke.ultra.common.Lookup
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.log
import io.ktor.request.uri
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.jvm.jvmName

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

    /**
     * Load the gui data stored under [bucket] and [filename]
     *
     *  TODO: inject logger and remove parameter [application]
     */
    fun loadGuiData(application: Application, bucket: String, filename: String): InsightsGuiData {

        // get all newest files
        val files = repository.get(bucket).listNewest()
        // get the actual file
        val file = repository.get(bucket).getFile(filename)
        val fileIdx = files.indexOf(file)
        // get the previous and next file
        val nextFile = if (fileIdx > 0) files[fileIdx - 1] else null
        val previousFile = if (fileIdx < files.size - 1) files[fileIdx + 1] else null

        // read file contents
        val recordString = String(file.getContentBytes())
        val insightsData = mapper.readValue<InsightsData>(recordString)

        val collectors = insightsData.collectors
            .map {
                try {
                    val cls = Class.forName(it.cls).kotlin

                    if (!cls.allSuperclasses.contains(InsightsCollectorData::class)) {
                        return@map null
                    }

                    return@map mapper.convertValue(it.data, cls.java) as InsightsCollectorData

                } catch (e: Throwable) {
                    application.log.warn(e.message)
                    return@map null
                }
            }
            .filterNotNull()

        return InsightsGuiData(
            insightsData.ts,
            insightsData.date,
            insightsData.chronos,
            collectors,
            nextFile,
            previousFile
        )
    }
}

