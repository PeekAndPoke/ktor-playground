package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.insights.gui.InsightsBarRenderer
import de.peekandpoke.ktorfx.insights.gui.insightsBar
import de.peekandpoke.ktorfx.semanticui.SemanticUi
import de.peekandpoke.ktorfx.semanticui.jQuery
import de.peekandpoke.ktorfx.semanticui.semanticUi
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

    /** I18n service injected from the kontainer */
    final override val t: I18n = tools.i18n
    /** FlashSession service injected from the kontainer */
    final override val flashSession: FlashSession = tools.flashSession
    /** WebResources service injected from the kontainer */
    final override val webResources: WebResources = tools.webResources
    /** InsightsBarRenderer service injected from the kontainer */
    final override val insightsBarRenderer: InsightsBarRenderer? = tools.insightsBarRenderer

    /** Latest entries in the FlashSession */
    val flashSessionEntries = flashSession.pull()

    /** The breadcrumbs of the page */
    override var breadCrumbs: List<Any> = listOf()

    /** Placeholder list for the page head */
    final override val pageHead = PlaceholderList<HEAD, HEAD>()

    /** The main menu of the page */
    final override val mainMenu = Placeholder<FlowContent>()

    /** The background color of the menu */
    override fun SemanticUi.mainMenuBgColor(): SemanticUi = violet

    /** Placeholder for the page content */
    final override val content = Placeholder<FlowContent>()

    /** Placeholder list for the pages styles */
    final override val styles = PlaceholderList<HEAD, HEAD>()
    /** Placeholder list for the pages scripts */
    final override val scripts = PlaceholderList<FlowContent, FlowContent>()

    /** Placeholder for the insights bar */
    val insightsBar = Placeholder<FlowContent>()

    /**
     * Call this to load the default jQuery version
     */
    protected fun loadDefaultJQuery() {
        styles {
            css(webResources.jQuery)
        }

        scripts {
            js(webResources.jQuery)
        }
    }

    /**
     * Call this to load semantic uis default theme and javascript
     *
     * If you a customized semantic ui, you need to load all resources individually
     */
    protected fun loadDefaultSemanticUi() {
        styles {
            css(webResources.semanticUi)
        }

        scripts {
            js(webResources.semanticUi)
        }
    }

    /**
     * Initializes and renders the insights bar
     */
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
