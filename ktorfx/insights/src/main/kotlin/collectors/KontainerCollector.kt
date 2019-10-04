package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ultra.kontainer.Kontainer
import io.ktor.application.ApplicationCall

class KontainerCollector(private val kontainer: Kontainer) : InsightsCollector {

    data class Data(val dump: String) : InsightsCollectorData

    override val name = "Kontainer"

    override fun finish(call: ApplicationCall) = Data(kontainer.dump())
}
