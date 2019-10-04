package de.peekandpoke.ktorfx.insights

import io.ktor.application.ApplicationCall

interface InsightsCollector {

    val name: String

    fun finish(call: ApplicationCall): InsightsCollectorData
}
