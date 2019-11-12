package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ktorfx.insights.gui.InsightsBarTemplate
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiTemplate
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import io.ktor.application.ApplicationCall
import kotlinx.html.FlowContent
import kotlinx.html.title

class RuntimeCollector : InsightsCollector {

    data class Data(
        val jvmVersion: String,
        val maxMem: Long,
        val reservedMem: Long,
        val freeMem: Long,
        val cpus: Int,
        val systemProperties: Map<String, String>
    ) : InsightsCollectorData {

        override fun renderBar(template: InsightsBarTemplate) = with(template) {

            left {

                ui.item {
                    title = "Number of available processors"

                    icon.microchip()

                    +"$cpus CPUs"
                }

                ui.item {

                    title = "Memory usage: free / reserved / max"

                    val maxGb = "%.2f".format(maxMem / 1_000_000_000.0)
                    val reservedGb = "%.2f".format(reservedMem / 1_000_000_000.0)
                    val freeGb = "%.2f".format(freeMem / 1_000_000_000.0)

                    +"$freeGb / $reservedGb / $maxGb GB"
                }
            }
        }

        override fun renderDetails(template: InsightsGuiTemplate) = with(template) {

            menu {
                icon.microchip()
                +"Runtime"
            }

            content {

                stats()

                ui.segment {
                    ui.header { +"System properties" }

                    ui.list {
                        systemProperties.forEach { (k, v) ->
                            ui.item {
                                +"$k: $v"
                            }
                        }
                    }
                }
            }
        }

        fun FlowContent.stats() {
            ui.horizontal.segments {

                ui.compact.center.aligned.segment {
                    ui.header { +"JVM" }
                    +jvmVersion
                }

                ui.compact.center.aligned.segment {
                    ui.header { +"CPUs" }
                    +cpus.toString()
                }

                ui.compact.center.aligned.segment {
                    ui.header { +"Free Heap" }
                    +"%d MB".format(freeMem / (1024 * 1024))
                }

                ui.compact.center.aligned.segment {
                    ui.header { +"Reserved Heap" }
                    +"%d MB".format(reservedMem / (1024 * 1024))
                }

                ui.compact.center.aligned.segment {
                    ui.header { +"Max Heap" }
                    +"%d MB".format(maxMem / (1024 * 1024))
                }
            }
        }
    }


    override fun finish(call: ApplicationCall): InsightsCollectorData {

        val rt = Runtime.getRuntime()

        return Data(
            System.getProperty("java.version"),
            rt.maxMemory(),
            rt.totalMemory(),
            rt.freeMemory(),
            rt.availableProcessors(),
            System.getProperties().map { (k, v) -> k.toString() to v.toString() }.toMap()
        )
    }

}
