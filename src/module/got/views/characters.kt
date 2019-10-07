package de.peekandpoke.module.got.views

import de.peekandpoke.karango.examples.game_of_thrones.Character
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.got.GameOfThronesRoutes
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.a
import kotlinx.html.span
import kotlinx.html.title

internal fun SimpleTemplate.characters(routes: GameOfThronesRoutes, characters: Iterable<Stored<Character>>) {

    breadCrumbs = listOf(GameOfThronesMenu.CHARACTERS)

    pageTitle {
        title { +"GoT Characters" }
    }

    content {

        ui.header H4 { +"List of Characters" }

        ui.list {
            characters.forEach {

                ui.item {
                    a(href = routes.getCharacter(it)) { +"${it.value.name} ${it.value.surname ?: ""} " }

                    span { +"${if (it.value.alive) "alive" else "dead"} " }

                    it.value.age?.let { span { +"Age: $it " } }

                    it.value.actor?.value?.let {
                        span { +"(Actor: ${it.name} ${it.surname} Age: ${it.age}) " }
                    }
                }
            }
        }
    }
}
