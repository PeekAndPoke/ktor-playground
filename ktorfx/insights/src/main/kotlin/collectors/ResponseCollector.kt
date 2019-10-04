package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.util.toMap

class ResponseCollector : InsightsCollector {

    data class Data(
        val status: HttpStatusCode?,
        val headers: Map<String, List<String>>
    ) : InsightsCollectorData

    override val name = "Response"

    override fun finish(call: ApplicationCall) = Data(
        call.response.status(),
        call.response.headers.allValues().toMap()
    )
}
