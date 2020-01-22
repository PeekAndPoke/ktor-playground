package de.peekandpoke.module.semanticui.views

import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import kotlinx.html.*

internal fun SimpleTemplate.playground() {

    breadCrumbs = listOf(SemanticUiMenu.Playground)

    pageHead {
        title { +"SemanticUI Playground" }
    }

    content {

        h1 {
            +"Site"
        }

        ui.three.column.stackable.divided.grid {

            ui.column {
                h1 { +"Heading 1" }
                h2 { +"Heading 2" }
                h3 { +"Heading 3" }
                h4 { +"Heading 4" }
                h5 { +"Heading 5" }
                h6 { +"Heading 6" }
            }

            ui.column {
                h2 { +"""Example body text""" }
                p {
                    +"""Nullam quis risus eget"""
                    a {
                        href = "#"
                        +"""urna mollis ornare"""
                    }
                    +"""vel eu leo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam id dolor id nibh ultricies vehicula."""
                }
                p {
                    small { +"""This line of text is meant to be treated as fine print.""" }
                }
                p {
                    +"""The following snippet of text is """
                    strong { +"""rendered as bold text""" }
                    +"""."""
                }
                p {
                    +"""The following snippet of text is """
                    em { +"""rendered as italicized text""" }
                    +"""."""
                }
                p {
                    +"""An abbreviation of the word attribute is """
                    abbr {
                        title = "attribute"
                        +"""attr"""
                    }
                    +"""."""
                }
            }

            ui.column {
                ui.three.column.stackable.padded.middle.aligned.centered.column.grid {

                    ui.red.column { +"Red" }
                    ui.orange.column { +"Orange" }
                    ui.yellow.column { +"Yellow" }
                    ui.olive.column { +"olive" }
                    ui.green.column { +"green" }
                    ui.teal.column { +"teal" }
                    ui.blue.column { +"blue" }
                    ui.violet.column { +"violet" }
                    ui.purple.column { +"purple" }
                    ui.pink.column { +"pink" }
                    ui.brown.column { +"brown" }
                    ui.grey.column { +"grey" }
                    ui.black.column { +"black" }
                }
            }
        }
    }
}
