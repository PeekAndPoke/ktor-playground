package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.insights.gui.InsightsBarRenderer
import de.peekandpoke.ktorfx.semanticui.SemanticUi
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ultra.polyglot.I18n
import io.ktor.html.Placeholder
import io.ktor.html.PlaceholderList
import io.ktor.html.Template
import kotlinx.html.FlowContent
import kotlinx.html.HEAD
import kotlinx.html.HTML

interface SimpleTemplate : Template<HTML> {

    /** The Template tools */
    val tools: TemplateTools
    /** Shorthand for accessing the i18n */
    val t: I18n
    /** Shorthand for accessing the flash session */
    val flashSession: FlashSession
    /** Shorthand for accessing the WebResources */
    val webResources: WebResources
    /** Shorthand for accessing the insights bar renderer */
    val insightsBarRenderer: InsightsBarRenderer?

    /**
     * The bread crumbs can be set by each template in order to:
     * - highlight selected main menus
     * - display bread crumbs
     */
    var breadCrumbs: List<Any>

    /** Placeholder for the page title */
    val pageTitle: Placeholder<HEAD>

    /** Placeholder for the main menu */
    val mainMenu: Placeholder<FlowContent>

    /** Return the background color of the main menu */
    fun SemanticUi.mainMenuBgColor(): SemanticUi

    /** Placeholder for the content */
    val content: Placeholder<FlowContent>

    /** Placeholder for the styles */
    val styles: PlaceholderList<HEAD, HEAD>
    /** Placeholder for the scripts */
    val scripts: PlaceholderList<FlowContent, FlowContent>
}
