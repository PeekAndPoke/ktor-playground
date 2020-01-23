package de.peekandpoke.ktorfx.insights.collectors

import com.fasterxml.jackson.module.kotlin.convertValue
import de.peekandpoke.ktorfx.common.config.AppConfig
import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ktorfx.insights.InsightsMapper
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiTemplate
import de.peekandpoke.ktorfx.semanticui.icon
import io.ktor.application.ApplicationCall

class AppConfigCollector(
    private val mapper: InsightsMapper,
    private val appConfig: AppConfig? = null
) : InsightsCollector {

    data class Data(val data: Any) : InsightsCollectorData {

        override fun renderDetails(template: InsightsGuiTemplate) = with(template) {

            menu {
                icon.cubes()
                +"AppConfig"
            }

            content {
                json(data)
            }
        }
    }

    override fun finish(call: ApplicationCall): Data {
        return Data(
            when {
                appConfig != null -> mapper.convertValue<Map<*, *>>(appConfig)
                else -> "not available"
            }
        )
    }
}
