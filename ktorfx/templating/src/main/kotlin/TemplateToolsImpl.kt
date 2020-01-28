package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.broker.TypedRouteRenderer
import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.insights.gui.InsightsBarRenderer
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ultra.polyglot.I18n

class TemplateToolsImpl(
    override val i18n: I18n,
    override val flashSession: FlashSession,
    override val webResources: WebResources,
    override val routeRenderer: TypedRouteRenderer,
    override val insightsBarRenderer: InsightsBarRenderer?,
    override val insightsCollector: TemplateInsightsCollector?
) : TemplateTools
