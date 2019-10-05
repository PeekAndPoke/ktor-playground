package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import io.ktor.application.ApplicationCall
import io.ktor.features.origin
import io.ktor.http.HttpMethod
import io.ktor.request.host
import io.ktor.request.httpMethod
import io.ktor.request.port
import io.ktor.request.uri
import io.ktor.util.toMap

class RequestCollector : InsightsCollector {

    data class Data(
        val method: HttpMethod,
        val scheme: String,
        val host: String,
        val port: Int,
        val uri: String,
        val headers: Map<String, List<String>>,
        val queryParams: Map<String, List<String>>
    ) : InsightsCollectorData {
        val fullUrl = "$scheme://$host:$port$uri"
    }

    override val name = "Request"

    override fun finish(call: ApplicationCall) = Data(
        call.request.httpMethod,
        call.request.origin.scheme,
        call.request.host(),
        call.request.port(),
        call.request.uri,
        call.request.headers.toMap(),
        call.request.queryParameters.toMap()
    )
}
