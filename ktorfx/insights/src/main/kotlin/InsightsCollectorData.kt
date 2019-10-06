package de.peekandpoke.ktorfx.insights

import de.peekandpoke.ktorfx.insights.gui.InsightsBarTemplate
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiTemplate
import de.peekandpoke.ktorfx.semanticui.ui
import kotlinx.html.DIV
import kotlinx.html.div

interface InsightsCollectorData {

    val templateKey: String get() = (this::class.qualifiedName ?: "unknown").toId()

    fun renderBar(template: InsightsBarTemplate) {}

    fun renderDetails(template: InsightsGuiTemplate) {}

    fun InsightsGuiTemplate.menu(block: DIV.() -> Unit) {

        menuPlaceholders {
            ui.item {
                attributes["data-key"] = templateKey

                block()
            }
        }
    }

    fun InsightsGuiTemplate.content(block: DIV.() -> Unit) {

        contentPlaceholders {
            div {
                attributes["data-key"] = templateKey

                ui.basic.segment {
                    block()
                }
            }
        }
    }

    private fun String.toId() = replace("[^a-zA-Z0-9]".toRegex(), "-")
}
