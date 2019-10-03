package de.peekandpoke.ktorfx.insights

import de.peekandpoke.ultra.common.Lookup
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

class Insights(
    private val collectors: Lookup<InsightsCollector>,
    private val repository: InsightsRepository,
    private val mapper: InsightsMapper
) {
    private val date = LocalDate.now()
    private val dateTime = LocalDateTime.now()
    private val ts = System.nanoTime()

    val bucket = "records-${date}"
    val filename = "$dateTime.$ts.json"

    fun <T : InsightsCollector, R> use(cls: KClass<T>, block: T.() -> R?): R? {
        return collectors.get(cls)?.block()
    }

    fun done() {

        val data = InsightsData(
            ts,
            dateTime.toString(),
            collectors.all().map {

                val dataCls = when {
                    it.data == null -> null
                    else -> it.data!!::class.jvmName
                }

                CollectorData(it::class.jvmName, dataCls, it.name, it.data)
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

