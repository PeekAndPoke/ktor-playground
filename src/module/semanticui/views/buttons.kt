package de.peekandpoke.module.semanticui.views

import de.peekandpoke.module.semanticui.Template
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.i

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal fun Template.buttons() {

    content {

        ui.button { +"Default" }
        ui.primary.button { +"Primary" }
        ui.secondary.button { +"Secondary" }
        ui.red.button { +"Red" }

        ui.animated.button {
            ui.visible.content { +"""Next""" }
            ui.hidden.content {
                i(classes = "right arrow icon")
            }
        }

        ui.vertical.animated.button {
            ui.hidden.content { +"""Shop""" }
            ui.visible.content {
                i(classes = "shop icon")
            }
        }

        ui.animated.fade.button {
            ui.visible.content { +"""Sign-up for a Pro account""" }
            ui.hidden.content { +"""$12.99 a month""" }
        }
    }

}
