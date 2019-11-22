package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.insights.gui.InsightsBarRenderer
import de.peekandpoke.ktorfx.insights.gui.insightsBar
import de.peekandpoke.ktorfx.semanticui.SemanticUi
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import de.peekandpoke.ultra.polyglot.I18n
import io.ktor.html.Placeholder
import io.ktor.html.PlaceholderList
import kotlinx.html.FlowContent
import kotlinx.html.HEAD

abstract class SimpleTemplateBase(

    final override val tools: TemplateTools

) : SimpleTemplate {

    final override val t: I18n = tools.i18n
    final override val flashSession: FlashSession = tools.flashSession
    final override val webResources: WebResources = tools.webResources
    final override val insightsBarRenderer: InsightsBarRenderer? = tools.insightsBarRenderer

    val flashSessionEntries = flashSession.pull()

    override var breadCrumbs: List<Any> = listOf()

    final override val pageTitle = Placeholder<HEAD>()

    final override val mainMenu = Placeholder<FlowContent>()
    override fun SemanticUi.mainMenuBgColor(): SemanticUi = violet

    final override val content = Placeholder<FlowContent>()

    final override val styles = PlaceholderList<HEAD, HEAD>()
    final override val scripts = PlaceholderList<FlowContent, FlowContent>()

    val insightsBar = Placeholder<FlowContent>()

    protected fun initInsights() {

        if (insightsBarRenderer != null) {

            styles {
                css(webResources.insightsBar)
            }
            scripts {
                js(webResources.insightsBar)
            }

            insightsBar {
                insightsBarRenderer.render(this)
            }
        }
    }
}
