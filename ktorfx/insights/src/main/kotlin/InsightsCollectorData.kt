package de.peekandpoke.ktorfx.insights

import de.peekandpoke.ktorfx.insights.gui.InsightsBarTemplate

interface InsightsCollectorData {

    fun renderBar(template: InsightsBarTemplate) {}

}
