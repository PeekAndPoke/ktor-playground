package de.peekandpoke.ktorfx.common

import io.ktor.application.ApplicationCall
import io.ktor.http.Parameters
import io.ktor.request.receiveParameters
import io.ktor.util.AttributeKey

val ReceivedParametersKey = AttributeKey<Parameters>("${'$'}KtorFx${'$'}ReceivedParametersKey")

suspend fun ApplicationCall.receiveOrGet(): Parameters {

    val receiver = suspend { receiveParameters() }

    return attributes.getOrNull(ReceivedParametersKey) ?: receiver().apply { attributes.put(ReceivedParametersKey, this) }
}
