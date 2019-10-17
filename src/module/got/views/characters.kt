package de.peekandpoke.module.got.views

import de.peekandpoke.karango.examples.game_of_thrones.Character
import de.peekandpoke.ktorfx.common.texts.people
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.got.GameOfThronesRoutes
import de.peekandpoke.module.got.got
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.*

internal fun SimpleTemplate.characters(routes: GameOfThronesRoutes, characters: Iterable<Stored<Character>>) {

    breadCrumbs = listOf(GameOfThronesMenu.CHARACTERS)

    pageTitle {
        title { +t { got.title_characters() } }
    }

    content {

        ui.header H4 { +t { got.title_characters() } }

        ui.celled.table Table {
            thead {
                tr {
                    th { +t { people.name } }
                    th { +t { people.age } }
                    th { +t { people.alive } }
                    th { +t { got.actor } }
                }
            }

            tbody {
                characters.forEach {
                    tr {
                        td {
                            a(href = routes.getCharacter(it)) { +"${it.value.name} ${it.value.surname ?: ""} " }
                        }

                        td {
                            it.value.age?.let { span { +"$it" } }
                        }

                        td {
                            span {
                                +if (it.value.alive) t { people.alive } else t { people.dead }
                            }
                        }

                        td {
                            it.value.actor?.value?.let {
                                span { +"${it.name} ${it.surname} ${t { people.age }}: ${it.age}" }
                            }
                        }
                    }
                }
            }
        }
    }
}

