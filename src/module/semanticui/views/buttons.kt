package de.peekandpoke.module.semanticui.views

import de.peekandpoke.module.semanticui.Template
import io.ultra.ktor_tools.prismjs.Lang
import io.ultra.ktor_tools.prismjs.prism
import io.ultra.ktor_tools.semanticui.icon
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.p

@Suppress("DuplicatedCode")
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
                            ui.inverted.red.button { +"Red" }
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
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Basic Inverted Buttons on inverted background" }
                ui.column {
                    ui.inverted.segment {
                        ui.basic.inverted.button { +"Default" }
                        ui.basic.inverted.primary.button { +"Primary" }
                        ui.basic.inverted.secondary.button { +"Secondary" }
                        ui.basic.inverted.red.button { +"Red" }
                        ui.basic.inverted.basic.red.button { +"Red" }
                        ui.basic.inverted.orange.button { +"Orange" }
                        ui.basic.inverted.yellow.button { +"Yellow" }
                        ui.basic.inverted.olive.button { +"Olive" }
                        ui.basic.inverted.green.button { +"Green" }
                        ui.basic.inverted.teal.button { +"Teal" }
                        ui.basic.inverted.blue.button { +"Blue" }
                        ui.basic.inverted.violet.button { +"Violet" }
                        ui.basic.inverted.purple.button { +"Purple" }
                        ui.basic.inverted.pink.button { +"Pink" }
                        ui.basic.inverted.brown.button { +"Brown" }
                        ui.basic.inverted.grey.button { +"Grey" }
                        ui.basic.inverted.black.button { +"Black" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.basic.inverted.button { +"Default" }
                            ui.basic.inverted.primary.button { +"Primary" }
                            ui.basic.inverted.secondary.button { +"Secondary" }
                            ui.basic.inverted.red.button { +"Red" }
                            ui.basic.inverted.orange.button { +"Orange" }
                            ui.basic.inverted.yellow.button { +"Yellow" }
                            ui.basic.inverted.olive.button { +"Olive" }
                            ui.basic.inverted.green.button { +"Green" }
                            ui.basic.inverted.teal.button { +"Teal" }
                            ui.basic.inverted.blue.button { +"Blue" }
                            ui.basic.inverted.violet.button { +"Violet" }
                            ui.basic.inverted.purple.button { +"Purple" }
                            ui.basic.inverted.pink.button { +"Pink" }
                            ui.basic.inverted.brown.button { +"Brown" }
                            ui.basic.inverted.grey.button { +"Grey" }
                            ui.basic.inverted.black.button { +"Black" }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Sizes" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"Buttons can have different sizes" }
                ui.column {
                    ui.mini.button { +"Mini" }
                    ui.tiny.button { +"Tiny" }
                    ui.small.button { +"Small" }
                    ui.medium.button { +"Medium" }
                    ui.large.button { +"Large" }
                    ui.big.button { +"Big" }
                    ui.huge.button { +"Huge" }
                    ui.massive.button { +"Massive" }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.mini.button { +"Mini" }
                            ui.tiny.button { +"Tiny" }
                            ui.small.button { +"Small" }
                            ui.medium.button { +"Medium" }
                            ui.large.button { +"Large" }
                            ui.big.button { +"Big" }
                            ui.huge.button { +"Huge" }
                            ui.massive.button { +"Massive" }
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

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"A button can have only an icon" }
                ui.column {
                    ui.icon.button {
                        icon.cloud()
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.icon.button {
                                icon.cloud()
                            }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"A button can use an icon as a label" }
                ui.column {
                    ui.labeled.icon.button {
                        icon.pause()
                        +"Pause"
                    }
                    ui.right.labeled.icon.button {
                        icon.arrow_right()
                        +"Next"
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.labeled.icon.button {
                                icon.pause()
                                +"Pause"
                            }
                            ui.right.labeled.icon.button {
                                icon.arrow_right()
                                +"Next"
                            }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"A basic button with icon" }
                ui.column {
                    ui.basic.button {
                        icon.user()
                        +"Add friend"
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.basic.button {
                                icon.user()
                                +"Add friend"
                            }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Groups" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"Buttons can exist together as a group" }
                ui.column {
                    ui.buttons {
                        ui.button Button { +"One" }
                        ui.button Button { +"Two" }
                        ui.button Button { +"Three" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.buttons {
                                ui.button Button { +"One" }
                                ui.button Button { +"Two" }
                                ui.button Button { +"Three" }
                            }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Buttons groups can show groups of icons" }
                ui.column {
                    ui.icon.buttons {
                        ui.button { icon.align_left() }
                        ui.button { icon.align_center() }
                        ui.button { icon.align_right() }
                        ui.button { icon.align_justify() }
                    }
                    ui.icon.buttons {
                        ui.button { icon.bold() }
                        ui.button { icon.underline() }
                        ui.button { icon.text_width() }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.icon.buttons {
                                ui.button { icon.align_left() }
                                ui.button { icon.align_center() }
                                ui.button { icon.align_right() }
                                ui.button { icon.align_justify() }
                            }
                            ui.icon.buttons {
                                ui.button { icon.bold() }
                                ui.button { icon.underline() }
                                ui.button { icon.text_width() }
                            }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Content" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"Buttons groups can contain conditionals" }
                ui.column {
                    ui.buttons {
                        ui.button.active Button { +"Cancel" }
                        div(classes = "or") {}
                        ui.button.positive Button { +"Save" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.buttons {
                                ui.button.active Button { +"Cancel" }
                                div(classes = "or")
                                ui.button.positive Button { +"Save" }
                            }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Buttons groups can contain conditionals with translation" }
                ui.column {
                    ui.buttons {
                        ui.button.active Button { +"un" }
                        div(classes = "or") { attributes["data-text"] = "ou" }
                        ui.button.positive Button { +"deux" }
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.buttons {
                                ui.button.active Button { +"un" }
                                div(classes = "or") { attributes["data-text"] = "ou" }
                                ui.button.positive Button { +"deux" }
                            }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"States" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"A button can show it is currently the active user selection" }
                ui.column {
                    ui.active.button {
                        icon.user()
                        +"Follow"
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.active.button {
                                icon.user()
                                +"Follow"
                            }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"A button can show it is currently unable to be interacted with" }
                ui.column {
                    ui.disabled.button {
                        icon.user()
                        +"Followed"
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.disabled.button {
                                icon.user()
                                +"Followed"
                            }
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"A button can show a loading indicator" }
                ui.column {
                    ui.loading.button { +"Loading" }
                    ui.basic.loading.button { +"Loading" }
                    ui.primary.loading.button { +"Loading" }
                    ui.secondary.loading.button { +"Loading" }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.loading.button { +"Loading" }
                            ui.basic.loading.button { +"Loading" }
                            ui.primary.loading.button { +"Loading" }
                            ui.secondary.loading.button { +"Loading" }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Floated" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"A button can be aligned to the left or right of its container" }
                ui.column {
                    ui.right.floated.button { +"Right Floated" }
                    ui.left.floated.button { +"Left Floated" }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.right.floated.button { +"Right Floated" }
                            ui.left.floated.button { +"Left Floated" }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Compact" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"A button can reduce its padding to fit into tighter spaces" }
                ui.column {
                    ui.compact.button { +"Hold" }
                    ui.compact.icon.button { icon.pause() }
                    ui.compact.labeled.icon.button {
                        icon.pause()
                        +"Pause"
                    }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.compact.button { +"Hold" }
                            ui.compact.icon.button { icon.pause() }
                            ui.compact.labeled.icon.button {
                                icon.pause()
                                +"Pause"
                            }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Toggle" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"A button can be formatted to toggle on and off" }
                ui.column {
                    ui.toggle.button Button { +"Vote" }
                    ui.toggle.button.active Button { +"Voted" }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.toggle.button Button { +"Vote" }
                            ui.toggle.button.active Button { +"Voted" }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Fluid" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"A button can take the width of its container" }
                ui.column {
                    ui.fluid.button Button { +"Fits container" }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.fluid.button Button { +"Fits container" }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Circular" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"A button can be circular" }
                ui.column {
                    ui.circular.icon.button Button { icon.settings() }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.circular.icon.button Button { icon.settings() }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Social variations" }

        ui.three.column.celled.grid {

            ui.row {
                ui.column { +"A button can be formatted to link to a social website" }
                ui.column {
                    ui.facebook.icon.button { icon.facebook(); +"Facebook" }
                    ui.circular.facebook.icon.button { icon.facebook() }
                    ui.twitter.icon.button { icon.twitter(); +"Twitter" }
                    ui.circular.twitter.icon.button { icon.twitter() }
                    ui.google_plus.icon.button { icon.google_plus(); +"Google Plus" }
                    ui.circular.google_plus.icon.button { icon.google_plus() }
                    ui.linkedin.icon.button { icon.linkedin(); +"Linkedin" }
                    ui.circular.linkedin.icon.button { icon.linkedin() }
                    ui.instagram.icon.button { icon.instagram(); +"Instagram" }
                    ui.circular.instagram.icon.button { icon.instagram() }
                    ui.youtube.icon.button { icon.youtube(); +"Youtube" }
                    ui.circular.youtube.icon.button { icon.youtube() }
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            ui.facebook.icon.button { icon.facebook(); +"Facebook" }
                            ui.circular.facebook.icon.button { icon.facebook() }
                            
                            ui.twitter.icon.button { icon.twitter(); +"Twitter" }
                            ui.google_plus.icon.button { icon.google_plus(); +"Google Plus" }
                            ui.linkedin.icon.button { icon.linkedin(); +"Linkedin" }
                            ui.instagram.icon.button { icon.instagram(); +"Instagram" }
                            ui.youtube.icon.button { icon.youtube(); +"Youtube" }
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"TODO ... there is more" }
    }
}
