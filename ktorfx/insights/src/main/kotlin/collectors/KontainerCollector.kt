package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ktorfx.insights.gui.InsightsBarTemplate
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiTemplate
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ultra.kontainer.Kontainer
import de.peekandpoke.ultra.kontainer.KontainerBlueprint
import io.ktor.application.ApplicationCall
import kotlinx.html.pre
import kotlinx.html.title

class KontainerCollector(private val kontainer: Kontainer, private val blueprint: KontainerBlueprint) : InsightsCollector {

    data class Data(
        val numOld: Int,
        val numTotal: Int,
        val dump: String
    ) : InsightsCollectorData {

        override fun renderBar(template: InsightsBarTemplate) = with(template) {

            left {

                ui.item {
                    title = "Kontainers in memory: young / old / total"

                    // TODO: make the magic numbers configurable
                    when {
                        numOld < 10 -> icon.green.cubes()
                        numOld < 50 -> icon.yellow.cubes()
                        else -> icon.red.cubes()
                    }

                    +"${numTotal - numOld} / $numOld / $numTotal"
                }
            }
        }

        override fun renderDetails(template: InsightsGuiTemplate) = with(template) {

            menu {
                icon.cubes()
                +"Kontainer"
            }

            content {

                pre { +dump }

                json(this@Data)
            }
        }
    }

    override fun finish(call: ApplicationCall): Data {

        val numTotal = blueprint.tracker.getNumAlive()

        // TODO: make the threshold configurable
        val numOld = blueprint.tracker.getNumAlive(15)

        // TODO: make this configurable: the threshold and if we should gc at all
        //       We need some mechanism to inject call-life-cycle hooks, that would deal with things like this
        if (numTotal > 50) {
            Runtime.getRuntime().gc()
        }

        return Data(numOld = numOld, numTotal = numTotal, dump = kontainer.dump())
    }
}
