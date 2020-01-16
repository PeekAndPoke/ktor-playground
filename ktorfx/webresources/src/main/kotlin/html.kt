package de.peekandpoke.ktorfx.webresources

import kotlinx.html.FlowOrMetaDataContent
import kotlinx.html.ScriptType
import kotlinx.html.link
import kotlinx.html.script

/**
 * Renders stylesheet tags for each css in the given [group]
 */
fun FlowOrMetaDataContent.css(group: WebResourceGroup) = group.css.forEach { css ->

    link(rel = "stylesheet", href = css.fullUri) {
//        css.integrity?.let { integrity = it }
    }
}

/**
 * renders script tags for each js in the given [group]
 */
fun FlowOrMetaDataContent.js(group: WebResourceGroup) = group.js.forEach { js ->
    script(type = ScriptType.textJavaScript, src = js.fullUri) {
        //        js.integrity?.let { integrity = it }
    }
}
