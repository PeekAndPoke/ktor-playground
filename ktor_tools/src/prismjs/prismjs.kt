package io.ultra.ktor_tools.prismjs

import kotlinx.html.FlowContent
import kotlinx.html.code
import kotlinx.html.pre

enum class Lang {
    Kotlin
}

fun FlowContent.prism(language: Lang, code: () -> String) {
    pre {
        code(classes = "highlight language-kotlin") {
            +code()
        }
    }
}
