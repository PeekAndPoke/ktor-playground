package de.peekandpoke.module.got.views

import de.peekandpoke.karango.examples.game_of_thrones.Character
import de.peekandpoke.ktorfx.common.texts.forms
import de.peekandpoke.ktorfx.common.texts.people
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.got.CharacterForm
import de.peekandpoke.module.got.GameOfThronesRoutes
import de.peekandpoke.module.got.got
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.title

fun SimpleTemplate.editCharacter(routes: GameOfThronesRoutes, character: Stored<Character>, form: CharacterForm) {

    breadCrumbs = listOf(GameOfThronesMenu.CHARACTERS, GameOfThronesMenu.EDIT_CHARACTER)

    pageHead {
        title { +"GoT Edit Characters" }
    }

    content {

        ui.header H4 { +t { got.edit_character(character.value.fullName) } }

        formidable(t, form) {

            ui.two.fields {
                textInput(form.id, label = t { people.name })
                textInput(form.surname, label = t { people.surname })
            }

            ui.two.fields {
                textInput(form.age, label = t { people.age })
                selectInput(form.alive, label = t { people.alive })
            }

            form.actor?.let { actorForm ->
                ui.header H4 { +t { got.edit_actor(character.value.actor?.value?.name) } }

                ui.three.fields {
                    textInput(actorForm.id, label = t { people.name })
                    textInput(actorForm.surname, label = t { people.surname })
                    numberInput(actorForm.age, label = t { people.age })
                }
            }

            ui.button Submit { +t { forms.submit } }
        }
    }
}
