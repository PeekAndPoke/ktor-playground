package de.peekandpoke.ktorfx.prismjs

import kotlinx.html.FlowContent
import kotlinx.html.code
import kotlinx.html.pre

fun FlowContent.prism(language: Language, code: () -> String) {
    pre {
        code(classes = "highlight language-${language.toString().toLowerCase()}") {
            +code()
        }
    }
}
