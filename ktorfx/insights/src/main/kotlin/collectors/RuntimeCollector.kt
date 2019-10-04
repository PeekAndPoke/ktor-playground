package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ktorfx.insights.gui.InsightsBarTemplate
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import io.ktor.application.ApplicationCall

class RuntimeCollector : InsightsCollector {

    data class Data(
        val maxMem: Long,
        val reservedMem: Long,
        val freeMem: Long,
        val cpus: Int
    ) : InsightsCollectorData {

        override fun renderBar(template: InsightsBarTemplate) = with(template) {

            left {

                ui.item {
                    icon.microchip()

                    +"$cpus CPUs"
                }

                ui.item {

                    val maxGb = "%.2f".format(maxMem / 1_000_000_000.0)
                    val reservedGb = "%.2f".format(reservedMem / 1_000_000_000.0)
                    val freeGb = "%.2f".format(freeMem / 1_000_000_000.0)


                    +"$freeGb / $reservedGb / $maxGb GB"
                }
            }
        }
    }

    override val name = "Runtime"

    override fun finish(call: ApplicationCall): InsightsCollectorData {

        val rt = Runtime.getRuntime()

        return Data(
            rt.maxMemory(),
            rt.totalMemory(),
            rt.freeMemory(),
            rt.availableProcessors()
        )
    }

}
