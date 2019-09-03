package de.peekandpoke.module.semanticui.views

import de.peekandpoke.module.semanticui.Template
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.prismjs.Lang
import io.ultra.ktor_tools.prismjs.prism
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.a
import kotlinx.html.h1
import kotlinx.html.i
import kotlinx.html.p

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal fun Template.buttons() {

    content {

        ui.vertical.basic.segment {
            h1 { +"Button" }
            p {
                +"A button indicates a possible user action. Click for "
                a(href = "https://semantic-ui.com/elements/button.html", target = "_blank") { +"Details" }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Default Button and Emphasis!" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"Default button as <div>" }
                ui.column { ui.button { +"Default" } }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.button { +"Default" }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Default button as <button>" }
                ui.column { ui.button Button { +"Default" } }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.button Button { +"Default" }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"'Primary' Button as <div>" }
                ui.column { ui.primary.button { +"Primary" } }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.primary.button { +"Primary" }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"'Primary' Button as <button>" }
                ui.column { ui.primary.button Button { +"Primary" } }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.primary.button Button { +"Primary" }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"'Secondary' Button as <div>" }
                ui.column { ui.secondary.button { +"Secondary" } }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.secondary.button { +"Secondary" }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"'Secondary' Button as <button>" }
                ui.column { ui.secondary.button Button { +"Secondary" } }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.secondary.button Button { +"Secondary" }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"'Positive' Button as <button>" }
                ui.column { ui.positive.button Button { +"Positive" } }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.positive.button Button { +"Positive" }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"'Negative' Button as <button>" }
                ui.column { ui.negative.button Button { +"Negative" } }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.negative.button Button { +"Negative" }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Colored Buttons" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"Buttons with colors" }
                ui.column {
                    ui.red.button { +"Red" }
                    ui.orange.button { +"Orange" }
                    ui.yellow.button { +"Yellow" }
                    ui.olive.button { +"olive" }
                    ui.green.button { +"green" }
                    ui.teal.button { +"teal" }
                    ui.blue.button { +"blue" }
                    ui.violet.button { +"violet" }
                    ui.purple.button { +"purple" }
                    ui.pink.button { +"pink" }
                    ui.brown.button { +"brown" }
                    ui.grey.button { +"grey" }
                    ui.black.button { +"black" }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.red.button { +"Red" }
                            ui.orange.button { +"Orange" }
                            ui.yellow.button { +"Yellow" }
                            ui.olive.button { +"olive" }
                            ui.green.button { +"green" }
                            ui.teal.button { +"teal" }
                            ui.blue.button { +"blue" }
                            ui.violet.button { +"violet" }
                            ui.purple.button { +"purple" }
                            ui.pink.button { +"pink" }
                            ui.brown.button { +"brown" }
                            ui.grey.button { +"grey" }
                            ui.black.button { +"black" }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Buttons with colors (basic)" }
                ui.column {
                    ui.basic.red.button { +"Red" }
                    ui.basic.basic.red.button { +"Red" }
                    ui.basic.orange.button { +"Orange" }
                    ui.basic.yellow.button { +"Yellow" }
                    ui.basic.olive.button { +"olive" }
                    ui.basic.green.button { +"green" }
                    ui.basic.teal.button { +"teal" }
                    ui.basic.blue.button { +"blue" }
                    ui.basic.violet.button { +"violet" }
                    ui.basic.purple.button { +"purple" }
                    ui.basic.pink.button { +"pink" }
                    ui.basic.brown.button { +"brown" }
                    ui.basic.grey.button { +"grey" }
                    ui.basic.black.button { +"black" }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.basic.red.button { +"Red" }
                            ui.basic.orange.button { +"Orange" }
                            ui.basic.yellow.button { +"Yellow" }
                            ui.basic.olive.button { +"olive" }
                            ui.basic.green.button { +"green" }
                            ui.basic.teal.button { +"teal" }
                            ui.basic.blue.button { +"blue" }
                            ui.basic.violet.button { +"violet" }
                            ui.basic.purple.button { +"purple" }
                            ui.basic.pink.button { +"pink" }
                            ui.basic.brown.button { +"brown" }
                            ui.basic.grey.button { +"grey" }
                            ui.basic.black.button { +"black" }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Animated Buttons" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"Default animation" }
                ui.column {
                    ui.animated.button {
                        ui.visible.content { +"Next" }
                        ui.hidden.content { i(classes = "right arrow icon") }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.animated.button {
                                ui.visible.content { +"Next" }
                                ui.hidden.content { i(classes = "right arrow icon") }
                            }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Vertical animation" }
                ui.column {
                    ui.vertical.animated.button {
                        ui.visible.content { i(classes = "shop icon") }
                        ui.hidden.content { +"Shop" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.vertical.animated.button {
                                ui.visible.content { i(classes = "shop icon") }
                                ui.hidden.content { +"Shop" }
                            }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Fade animation" }
                ui.column {
                    ui.animated.fade.button {
                        ui.visible.content { +"Sign-up for a Pro account" }
                        ui.hidden.content { +"$12.99 a month" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.animated.fade.button {
                                ui.visible.content { +"Sign-up for a Pro account" }
                                ui.hidden.content { +"${'$'}12.99 a month" }
                            }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Labeled Buttons" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"Labeled button" }
                ui.column {
                    ui.labeled.button {
                        ui.button { i(classes = "heart icon") }
                        a(classes = "ui basic label") { +"2,048" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                        ui.labeled.button {
                            ui.button { i(classes = "heart icon") }
                            a(classes = "ui basic label") { +"2,048" }
                        }
                    """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Left labeled button" }
                ui.column {
                    ui.left.labeled.button {
                        ui.basic.right.pointing.label A { +"2,048" }
                        ui.button { i(classes = "heart icon") }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                        ui.left.labeled.button {
                            ui.basic.right.pointing.label A { +"2,048" } 
                            ui.button { i(classes = "heart icon") }
                        }
                    """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Right labeled red button" }
                ui.column {
                    ui.labeled.button {
                        ui.red.button {
                            i(classes = "heart icon")
                            +"Like"
                        }
                        ui.basic.red.left.pointing.label A { +"2,048" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.labeled.button {
                                ui.red.button {
                                    i(classes = "heart icon")
                                    +"Like"
                                }
                                ui.basic.red.left.pointing.label A { +"2,048" }
                            }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Right labeled basic blue button" }
                ui.column {
                    ui.labeled.button {
                        ui.basic.blue.button {
                            i(classes = "fork icon")
                            +"Forks"
                        }
                        ui.basic.blue.left.pointing.label A { +"2,048" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.labeled.button {
                                ui.basic.blue.button {
                                    i(classes = "fork icon")
                                    +"Forks"
                                }
                                ui.basic.blue.left.pointing.label A { +"2,048" }
                            }
                        """.trimIndent()
                    }
                }

            }
        }
    }
}
