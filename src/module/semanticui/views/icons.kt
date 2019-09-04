package de.peekandpoke.module.semanticui.views

import de.peekandpoke.module.semanticui.Template
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.prismjs.Lang
import io.ultra.ktor_tools.prismjs.prism
import io.ultra.ktor_tools.semanticui.icon
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.*

@Suppress("DuplicatedCode")
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal fun Template.icons() {

    content {

        style("text/css") {
            +"""
            i.icon {
                font-size: 2em;
                margin: 0em auto 0.25em;
                display: block;
            }
        """.trimIndent()
        }

        ui.vertical.basic.segment {
            h1 { +"Icon" }
            p {
                +"An icon is a glyph used to represent something else. Click for "
                a(href = "https://semantic-ui.com/elements/icon.html", target = "_blank") { +"Details" }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"General usage" }

        ui.three.column.grid {

            ui.row {
                ui.column {
                    +"All icons are available through the "
                    b { +"icon" }
                    +" helper"
                }
                ui.column {
                    icon.question_circle()
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                        icon.question_circle()
                    """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column {
                    +"Some icons have an "
                    b { +"outline" }
                    +" version"
                }
                ui.column {
                    icon.question_circle.outline()
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                        icon.question_circle.outline()
                    """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Accessibility" }

        ui.ten.column.grid {
            ui.column {
                icon.american_sign_language_interpreting()
                +"american sign language interpreting"
            }

            ui.column {
                icon.assistive_listening_systems()
                +"assistive listening systems"
            }

            ui.column {
                icon.audio_description()
                +"audio description"
            }

            ui.column {
                icon.blind()
                +"blind"
            }

            ui.column {
                icon.braille()
                +"braille"
            }

            ui.column {
                icon.closed_captioning()
                +"closed captioning"
            }

            ui.column {
                icon.closed_captioning.outline()
                +"closed captioning outline"
            }

            ui.column {
                icon.deaf()
                +"deaf"
            }

            ui.column {
                icon.low_vision()
                +"low vision"
            }

            ui.column {
                icon.phone_volume()
                +"phone volume"
            }

            ui.column {
                icon.question_circle()
                +"question circle"
            }

            ui.column {
                icon.question_circle.outline()
                +"question circle outline"
            }

            ui.column {
                icon.sign_language()
                +"sign language"
            }

            ui.column {
                icon.tty()
                +"tty"
            }

            ui.column {
                icon.universal_access()
                +"universal access"
            }

            ui.column {
                icon.wheelchair()
                +"wheelchair"
            }
        }

    }
}
