package com.thebase.apps.cms.elements.common

import kotlinx.html.FlowContent
import kotlinx.html.br

/**
 * Renders the [content] as the text node of the current [FlowContent] while replacing newlines with <br>
 */
fun FlowContent.nl2br(content: String) {

    val parts = content.split("\n")

    parts.forEachIndexed { idx, part ->
        +part

        if (idx < parts.size - 1) {
            br { }
        }
    }
}
