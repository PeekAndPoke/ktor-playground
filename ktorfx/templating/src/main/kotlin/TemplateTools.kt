package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.insights.gui.InsightsBarRenderer
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ultra.polyglot.I18n

interface TemplateTools {
    val i18n: I18n
    val flashSession: FlashSession
    val webResources: WebResources
    val insightsBarRenderer: InsightsBarRenderer?
    val insightsCollector: TemplateInsightsCollector?
}
