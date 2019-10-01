package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ultra.polyglot.I18n
import io.ktor.html.Placeholder
import io.ktor.html.PlaceholderList
import io.ktor.html.Template
import kotlinx.html.FlowContent
import kotlinx.html.HEAD
import kotlinx.html.HTML

interface SimpleTemplate : Template<HTML> {

    val t: I18n
    val flashSession: FlashSession
    val webResources: WebResources

    /**
     * The bread crumbs can be set by each template in order to:
     * - highlight selected main menus
     * - display bread crumbs
     */
    var breadCrumbs: List<Any>

    val pageTitle: Placeholder<HEAD>
    val mainMenu: Placeholder<FlowContent>
    val content: Placeholder<FlowContent>

    val styles: PlaceholderList<HEAD, HEAD>
    val scripts: PlaceholderList<FlowContent, FlowContent>
}
