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

    val tools: TemplateTools

    val t: I18n
    val flashSession: FlashSession
    val webResources: WebResources
    val insights: InsightsBarRenderer?

    /**
     * The bread crumbs can be set by each template in order to:
     * - highlight selected main menus
     * - display bread crumbs
     */
    var breadCrumbs: List<Any>

    val pageTitle: Placeholder<HEAD>

    val mainMenu: Placeholder<FlowContent>
    fun SemanticUi.menuColor(): SemanticUi

    val content: Placeholder<FlowContent>

    val styles: PlaceholderList<HEAD, HEAD>
    val scripts: PlaceholderList<FlowContent, FlowContent>
}
