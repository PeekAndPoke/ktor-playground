package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiTemplate
import de.peekandpoke.ktorfx.semanticui.icon
import io.ktor.application.ApplicationCall
import kotlinx.html.pre

class RoutingCollector : InsightsCollector {

    data class Data(
        val trace: String? = null
    ) : InsightsCollectorData {

        override fun renderDetails(template: InsightsGuiTemplate) = with(template) {
            menu {
                icon.compass_outline()
                +"Routing"
            }

            content {

                pre { +(trace ?: "???") }

                json(this@Data)
            }
        }
    }

    private var data: Data = Data()

    override fun finish(call: ApplicationCall): InsightsCollectorData {
        return data
    }

    fun recordTrace(trace: String) {
        data = data.copy(trace = trace)
    }
}
