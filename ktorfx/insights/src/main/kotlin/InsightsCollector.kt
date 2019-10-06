package de.peekandpoke.ktorfx.insights

import io.ktor.application.ApplicationCall

interface InsightsCollector {
    fun finish(call: ApplicationCall): InsightsCollectorData
}
