package de.peekandpoke.module.semanticui.views

import de.peekandpoke.module.semanticui.Template
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.prismjs.Lang
import io.ultra.ktor_tools.prismjs.prism
import io.ultra.ktor_tools.semanticui.icon
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.a
import kotlinx.html.h1
import kotlinx.html.p

@Suppress("DuplicatedCode")
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
                    ui.olive.button { +"Olive" }
                    ui.green.button { +"Green" }
                    ui.teal.button { +"Teal" }
                    ui.blue.button { +"Blue" }
                    ui.violet.button { +"Violet" }
                    ui.purple.button { +"Purple" }
                    ui.pink.button { +"Pink" }
                    ui.brown.button { +"Brown" }
                    ui.grey.button { +"Grey" }
                    ui.black.button { +"Black" }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.red.button { +"Red" }
                            ui.orange.button { +"Orange" }
                            ui.yellow.button { +"Yellow" }
                            ui.olive.button { +"Olive" }
                            ui.green.button { +"Green" }
                            ui.teal.button { +"Teal" }
                            ui.blue.button { +"Blue" }
                            ui.violet.button { +"Violet" }
                            ui.purple.button { +"Purple" }
                            ui.pink.button { +"Pink" }
                            ui.brown.button { +"Brown" }
                            ui.grey.button { +"Grey" }
                            ui.black.button { +"Black" }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Basic Buttons are less pronounced" }
                ui.column {
                    ui.basic.button { +"Default" }
                    ui.basic.primary.button { +"Primary" }
                    ui.basic.secondary.button { +"Secondary" }
                    ui.basic.positive.button { +"Positive" }
                    ui.basic.negative.button { +"Negative" }
                    ui.basic.red.button { +"Red" }
                    ui.basic.basic.red.button { +"Red" }
                    ui.basic.orange.button { +"Orange" }
                    ui.basic.yellow.button { +"Yellow" }
                    ui.basic.olive.button { +"Olive" }
                    ui.basic.green.button { +"Green" }
                    ui.basic.teal.button { +"Teal" }
                    ui.basic.blue.button { +"Blue" }
                    ui.basic.violet.button { +"Violet" }
                    ui.basic.purple.button { +"Purple" }
                    ui.basic.pink.button { +"Pink" }
                    ui.basic.brown.button { +"Brown" }
                    ui.basic.grey.button { +"Grey" }
                    ui.basic.black.button { +"Black" }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.basic.button { +"Default" }
                            ui.basic.primary.button { +"Primary" }
                            ui.basic.secondary.button { +"Secondary" }
                            ui.basic.positive.button { +"Positive" }
                            ui.basic.negative.button { +"Negative" }
                            ui.basic.red.button { +"Red" }
                            ui.basic.orange.button { +"Orange" }
                            ui.basic.yellow.button { +"Yellow" }
                            ui.basic.olive.button { +"Olive" }
                            ui.basic.green.button { +"Green" }
                            ui.basic.teal.button { +"Teal" }
                            ui.basic.blue.button { +"Blue" }
                            ui.basic.violet.button { +"Violet" }
                            ui.basic.purple.button { +"Purple" }
                            ui.basic.pink.button { +"Pink" }
                            ui.basic.brown.button { +"Brown" }
                            ui.basic.grey.button { +"Grey" }
                            ui.basic.black.button { +"Black" }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Basic Buttons on inverted background" }
                ui.column {
                    ui.inverted.segment {
                        ui.inverted.button { +"Default" }
                        ui.inverted.primary.button { +"Primary" }
                        ui.inverted.secondary.button { +"Secondary" }
                        ui.inverted.positive.button { +"Positive" }
                        ui.inverted.negative.button { +"Negative" }
                        ui.inverted.red.button { +"Red" }
                        ui.inverted.basic.red.button { +"Red" }
                        ui.inverted.orange.button { +"Orange" }
                        ui.inverted.yellow.button { +"Yellow" }
                        ui.inverted.olive.button { +"Olive" }
                        ui.inverted.green.button { +"Green" }
                        ui.inverted.teal.button { +"Teal" }
                        ui.inverted.blue.button { +"Blue" }
                        ui.inverted.violet.button { +"Violet" }
                        ui.inverted.purple.button { +"Purple" }
                        ui.inverted.pink.button { +"Pink" }
                        ui.inverted.brown.button { +"Brown" }
                        ui.inverted.grey.button { +"Grey" }
                        ui.inverted.black.button { +"Black" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.inverted.button { +"Default" }
                            ui.inverted.primary.button { +"Primary" }
                            ui.inverted.secondary.button { +"Secondary" }
                            ui.inverted.positive.button { +"Positive" }
                            ui.inverted.negative.button { +"Negative" }
                            ui.basic.red.button { +"Red" }
                            ui.basic.orange.button { +"Orange" }
                            ui.basic.yellow.button { +"Yellow" }
                            ui.basic.olive.button { +"Olive" }
                            ui.basic.green.button { +"Green" }
                            ui.basic.teal.button { +"Teal" }
                            ui.basic.blue.button { +"Blue" }
                            ui.basic.violet.button { +"Violet" }
                            ui.basic.purple.button { +"Purple" }
                            ui.basic.pink.button { +"Pink" }
                            ui.basic.brown.button { +"Brown" }
                            ui.basic.grey.button { +"Grey" }
                            ui.basic.black.button { +"Black" }
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
                        ui.hidden.content { icon.arrow_right() }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.animated.button {
                                ui.visible.content { +"Next" }
                                ui.hidden.content { icon.arrow_right() }
                            }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Vertical animation" }
                ui.column {
                    ui.vertical.animated.button {
                        ui.visible.content { icon.shop() }
                        ui.hidden.content { +"Shop" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.vertical.animated.button {
                                ui.visible.content { icon.shop() }
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
                        ui.button { icon.heart() }
                        ui.basic.label A { +"2,048" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                        ui.labeled.button {
                            ui.button { icon.heart() }
                            ui.basic.label A { +"2,048" }
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
                        ui.button { icon.heart() }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                        ui.left.labeled.button {
                            ui.basic.right.pointing.label A { +"2,048" } 
                            ui.button { icon.heart() }
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
                            icon.heart()
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
                                    icon.heart()
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
                            icon.fork()
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
                                    icon.fork()
                                    +"Forks"
                                }
                                ui.basic.blue.left.pointing.label A { +"2,048" }
                            }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Buttons with icons" }

        // TODO: continue

    }
}
