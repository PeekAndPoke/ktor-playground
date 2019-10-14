package de.peekandpoke.module.got.views

import de.peekandpoke.karango.examples.game_of_thrones.Character
import de.peekandpoke.ktorfx.formidable.semanticui.formidable
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.got.CharacterForm
import de.peekandpoke.module.got.GameOfThronesRoutes
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.title

fun SimpleTemplate.editCharacter(routes: GameOfThronesRoutes, character: Stored<Character>, form: CharacterForm) {

    breadCrumbs = listOf(GameOfThronesMenu.CHARACTERS, GameOfThronesMenu.EDIT_CHARACTER)

    pageTitle {
        title { +"GoT Edit Characters" }
    }

    content {

        ui.header H4 { +"Edit Character ${character.value.fullName}" }

        formidable(t, form) {

            ui.two.fields {
                textInput(form.name, label = "Name")
                textInput(form.surname, label = "Surname")
            }

            ui.two.fields {
                textInput(form.age, label = "Age")
                selectInput(form.alive, label = "Alive")
            }

            form.actor?.let { actorForm ->
                ui.header H4 { +"Edit Actor ${character.value.actor?.value?.name}" }

                ui.three.fields {
                    textInput(actorForm.name, label = "Name")
                    textInput(actorForm.surname, label = "Surname")
                    numberInput(actorForm.age, label = "Age")
                }
            }

            ui.button Submit { +"Submit" }
        }
    }
}
