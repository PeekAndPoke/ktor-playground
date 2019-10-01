package de.peekandpoke.ultra.insights

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.peekandpoke.ultra.common.Lookup
import java.time.LocalDate
import java.time.LocalDateTime

class Insights(
    private val collectors: Lookup<InsightsCollector>,
    private val repository: InsightsRepository,
    private val mapper: InsightsMapper
) {
    fun done() {

        val nowDate = LocalDate.now()
        val nowTime = LocalDateTime.now()
        val now = System.nanoTime()

        val data = InsightsData(
            now,
            nowTime.toString(),
            collectors.all().map { CollectorData(it::class.qualifiedName!!, it.name, it.data) }
        )

        repository
            .createBucket("records-${nowDate}")
            .putFile(
                "$nowTime.$now.json",
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

        // serialization features
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)

        // deserialization features
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    }
}
