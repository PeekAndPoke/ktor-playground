package de.peekandpoke.ktorfx.insights

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.peekandpoke.ultra.common.Lookup
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass

class Insights(
    private val collectors: Lookup<InsightsCollector>,
    private val repository: InsightsRepository,
    private val mapper: InsightsMapper
) {

    private val date = LocalDate.now()
    private val dateTime = LocalDateTime.now()
    private val ts = System.nanoTime()

    val filename = "$dateTime.$ts.json"

    fun <T : InsightsCollector, R> use(cls: KClass<T>, block: T.() -> R?): R? {
        return collectors.get(cls)?.block()
    }

    fun done() {

        val data = InsightsData(
            ts,
            dateTime.toString(),
            collectors.all().map { CollectorData(it::class.qualifiedName!!, it.name, it.data) }
        )

        repository
            .createBucket("records-${date}")
            .putFile(
                filename,
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data)
            )
    }
}

data class InsightsData(
    val ts: Long,
    val date: String,
    val collectors: List<CollectorData>
)

data class CollectorData(val cls: String, val name: String, val data: Any?)

class InsightsMapper : ObjectMapper() {

    init {
        registerModule(KotlinModule())
        registerModule(Jdk8Module())
        registerModule(JavaTimeModule())

        // serialization features
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)

        // deserialization features
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
}
