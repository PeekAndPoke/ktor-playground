package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import io.ktor.application.ApplicationCall
import io.ktor.request.host
import io.ktor.request.port
import io.ktor.request.uri
import io.ktor.util.toMap

class RequestCollector : InsightsCollector {

    data class Data(
        val host: String,
        val port: Int,
        val uri: String,
        val headers: Map<String, List<String>>,
        val queryParams: Map<String, List<String>>
    )

    override val name = "Request"

    override var data: Data? = null

    fun record(call: ApplicationCall) {
        data = Data(
            call.request.host(),
            call.request.port(),
            call.request.uri,
            call.request.headers.toMap(),
            call.request.queryParameters.toMap()
        )
    }
}
