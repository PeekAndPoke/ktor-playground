package de.peekandpoke.module.got

import de.peekandpoke.karango.examples.game_of_thrones.*
import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.broker.getOrPost
import de.peekandpoke.ktorfx.flashsession.flashSession
import de.peekandpoke.ktorfx.flashsession.success
import de.peekandpoke.ktorfx.formidable.semanticui.numberInput
import de.peekandpoke.ktorfx.formidable.semanticui.selectInput
import de.peekandpoke.ktorfx.formidable.semanticui.textInput
import de.peekandpoke.module.got.view.MainTemplate
import de.peekandpoke.resources.WELCOME
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.vault.Stored
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ultra.ktor_tools.database
import kotlinx.html.*

val GameOfThronesModule = module {
    // config
    config("gotMountPoint", "/game-of-thrones")

    // application
    singleton(GameOfThronesRoutes::class)
    singleton(GameOfThrones::class)

    // database
    singleton(CharactersRepository::class)
    singleton(ActorsRepository::class)
}

class GameOfThronesRoutes(converter: OutgoingConverter, gotMountPoint: String) : Routes(converter, gotMountPoint) {

    data class GetCharacters(val page: Int = 1, val epp: Int = 100)

    val getCharacters = route<GetCharacters>("/characters")
    fun getCharacters(page: Int = 1, epp: Int = 100) = getCharacters(GetCharacters(page, epp))

    data class GetCharacter(val character: Stored<Character>)

    val getCharacter = route<GetCharacter>("/characters/{character}")
    fun getCharacter(character: Stored<Character>) = getCharacter(GetCharacter(character))
}

class GameOfThrones(val routes: GameOfThronesRoutes) {

    fun mount(route: Route) = with(route) {

        get(routes.getCharacters) { p ->

            //                val savedCharacters: Cursor<Stored<Character>> = database.actors.query {
//                    FOR(Characters) { c ->
//                        RETURN(c.AS<Stored<Character>>())
//                    }
//                }
//                println(savedCharacters.firstOrNull())
//
//                val savedActors: Cursor<Stored<Actor>> = database.actors.query {
//                    FOR(Actors) { c ->
//                        RETURN(c.AS<Stored<Actor>>())
//                    }
//                }
//                println(savedActors.firstOrNull())
//
//                val withActors = database.characters.findAllWithActor()
//                println(withActors.query)
//                println(withActors.toList().joinToString("\n"))

            val result = database.characters.findAllPaged(p.page, p.epp)

            val list = result.toList()
            val flashEntries = flashSession.pull()

            call.respondHtmlTemplate(MainTemplate(call)) {

                content {
                    div {

                        flashEntries.takeIf { it.isNotEmpty() }?.let { entries ->
                            div {
                                entries.forEach { entry -> div(classes = "alert alert-${entry.type}") { +entry.message } }
                            }
                        }

                        h2 { +t.WELCOME() }

                        h4 { +"List of Characters" }

                        a { href = routes.getCharacters(10, 2); +"Paging" }

                        ul {
                            list.forEach {

                                li {
                                    a(href = routes.getCharacter(it)) { +"${it.name} ${it.surname ?: ""} " }

                                    span { +"${if (it.alive) "alive" else "dead"} " }

                                    it.age?.let { span { +"Age: $it " } }

                                    it.actor?.value?.let {
                                        span { +"(Actor: ${it.name} ${it.surname} Age: ${it.age}) " }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        getOrPost(routes.getCharacter) { data ->

            val form = CharacterForm.of(data.character)

            println(data.character)

            if (form.submit(call)) {
                if (form.isModified) {

                    form.result.value.actor?.let { database.actors.save(it.asStored) }
                    val saved = database.characters.save(form.result)

                    flashSession.success("Character ${saved.value.fullName} was saved")
                }

                return@getOrPost call.respondRedirect(routes.getCharacters())
            }

            call.respondHtmlTemplate(MainTemplate(call)) {

                pageTitle {
                    title { +"GoT Edit Character" }
                }

                content {
                    div {

                        h4 { +"Edit Character ${data.character.fullName}" }

                        form(method = FormMethod.post) {

                            div {
                                div {
                                    div {
                                        textInput(t, form.name, label = "Name")
                                    }
                                    div {
                                        textInput(t, form.surname, label = "Surname")
                                    }
                                    div {
                                        textInput(t, form.age, label = "Age")
                                    }
                                    div {
                                        selectInput(t, form.alive, label = "Alive")
                                    }
                                }
                            }

                            form.actor?.let { actorForm ->
                                h4 { +"Edit Actor ${data.character.actor?.value?.name}" }

                                div {
                                    div {
                                        textInput(t, actorForm.name, label = "Name")
                                    }

                                    div {
                                        textInput(t, actorForm.surname, label = "Surname")
                                    }

                                    div {
                                        numberInput(t, actorForm.age, label = "Age")
                                    }
                                }
                            }

                            button(type = ButtonType.submit) { +"Submit" }
                        }
                    }
                }
            }
        }
    }
}


