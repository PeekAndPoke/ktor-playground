package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import io.ktor.application.ApplicationCall

class PipelinePhasesCollector : InsightsCollector {

    class Data : InsightsCollectorData {
        val phases = mutableListOf<Pair<String, Long>>()
    }

    private val data = Data()

    override fun finish(call: ApplicationCall) = data

    fun record(phase: String, time: Long) {
        data.phases.add(phase to time)
    }
}
