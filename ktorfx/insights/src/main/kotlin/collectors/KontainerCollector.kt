package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ultra.kontainer.Kontainer

class KontainerCollector : InsightsCollector {

    override val name = "Kontainer"

    override var data: List<String> = listOf()

    fun record(kontainer: Kontainer) {
        data = kontainer.dump().split("\n")
    }
}
