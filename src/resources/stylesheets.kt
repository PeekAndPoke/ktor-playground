package de.peekandpoke.resources

import azadev.kotlin.css.color
import io.ultra.ktor_tools.css

val GlobalStyles by lazy {
    mapOf("styles1.css" to styles1)
}

private val styles1 = css {

    div {
        h2 {
            color = "green"
        }
    }
}
