package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.insights.Insights
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ultra.polyglot.I18n

class TemplateToolsImpl(
    override val i18n: I18n,
    override val flashSession: FlashSession,
    override val webResources: WebResources,
    override val insights: Insights?
) : TemplateTools
