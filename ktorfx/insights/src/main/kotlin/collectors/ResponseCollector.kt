package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.util.toMap

class ResponseCollector : InsightsCollector {

    data class Data(
        val status: HttpStatusCode?,
        val headers: Map<String, List<String>>
    )

    override val name = "Response"

    override var data: Data? = null

    fun record(call: ApplicationCall) {
        data = Data(
            call.response.status(),
            call.response.headers.allValues().toMap()
        )
    }
}
