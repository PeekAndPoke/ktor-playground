package de.peekandpoke.module.got.views

import de.peekandpoke.karango.examples.game_of_thrones.Character
import de.peekandpoke.ktorfx.formidable.semanticui.numberInput
import de.peekandpoke.ktorfx.formidable.semanticui.selectInput
import de.peekandpoke.ktorfx.formidable.semanticui.textInput
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.got.CharacterForm
import de.peekandpoke.module.got.GameOfThronesRoutes
import de.peekandpoke.ultra.vault.Stored
import kotlinx.html.FormMethod
import kotlinx.html.title

fun SimpleTemplate.editCharacter(routes: GameOfThronesRoutes, character: Stored<Character>, form: CharacterForm) {

    breadCrumbs = listOf(GameOfThronesMenu.CHARACTERS, GameOfThronesMenu.EDIT_CHARACTER)

    pageTitle {
        title { +"GoT Edit Characters" }
    }

    content {

        ui.header H4 { +"Edit Character ${character.value.fullName}" }

        ui.form Form {
            method = FormMethod.post

            ui.two.fields {
                textInput(t, form.name, label = "Name")
                textInput(t, form.surname, label = "Surname")
            }

            ui.two.fields {
                textInput(t, form.age, label = "Age")
                selectInput(t, form.alive, label = "Alive")
            }

            form.actor?.let { actorForm ->
                ui.header H4 { +"Edit Actor ${character.value.actor?.value?.name}" }

                ui.three.fields {
                    textInput(t, actorForm.name, label = "Name")
                    textInput(t, actorForm.surname, label = "Surname")
                    numberInput(t, actorForm.age, label = "Age")
                }
            }

            ui.button Submit { +"Submit" }
        }
    }
}
