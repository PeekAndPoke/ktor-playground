package de.peekandpoke.module.got

import de.peekandpoke.karango.examples.game_of_thrones.*
import de.peekandpoke.karango_ktor.database
import de.peekandpoke.resources.MainTemplate
import de.peekandpoke.resources.WELCOME
import de.peekandpoke.ultra.vault.Stored
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.architecture.LinkGenerator
import io.ultra.ktor_tools.architecture.Module
import io.ultra.ktor_tools.bootstrap.*
import io.ultra.ktor_tools.flashSession
import io.ultra.ktor_tools.getOrPost
import io.ultra.ktor_tools.logger.logger
import kotlinx.html.*

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.gameOfThrones() = GameOfThronesModule(this)

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class GameOfThronesModule(app: Application) : Module(app) {

    val config = GameOfThronesConfig.from(application.environment.config)

    @Location("/characters")
    internal class GetCharacters(val page: Int = 1, val epp: Int = 100)

    @Location("/characters/{character}")
    internal class GetCharacter(val character: Stored<Character>)

    inner class LinkTo : LinkGenerator(config.mountPoint, application) {
        fun getCharacters() = linkTo(GetCharacters())
        fun getCharacterByKey(character: Stored<Character>) = linkTo(GetCharacter(character))
    }

    private val linkTo = LinkTo()

    override fun mount(mountPoint: Route) {

        mountPoint.route(config.mountPoint) {

            get<GetCharacters> { p ->

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

                println("-------------------------------------------------------------------------")
                println(result.query.ret.getType())

                call.respondHtmlTemplate(MainTemplate(call)) {

                    content {
                        container_fluid {

                            flashEntries.takeIf { it.isNotEmpty() }?.let { entries ->
                                div {
                                    entries.forEach { entry -> div(classes = "alert alert-${entry.type}") { +entry.message } }
                                }
                            }

                            h2 { +t.WELCOME() }

                            h4 { +"List of Characters" }

                            ul {
                                list.forEach {

                                    li {
                                        a(href = linkTo.getCharacterByKey(it)) { +"${it.name} ${it.surname ?: ""} " }

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

            getOrPost<GetCharacter> { data ->

                val form = CharacterForm(data.character._id, data.character.value.mutator())

                if (form.submit(call)) {

                    if (form.isModified) {

                        val savedActor = form.result.actor?.let { database.actors.save(it.asStored) }
                        val saved = database.characters.save(form.result)

                        logger.info("Updated character in database: $saved, $savedActor")

                        flashSession.success("Character ${form.result.fullName} was saved")
                    }

                    return@getOrPost call.respondRedirect(linkTo.getCharacters())
                }

                call.respondHtmlTemplate(MainTemplate(call)) {

                    pageTitle {
                        title { +"GoT Edit Character" }
                    }

                    content {
                        container_fluid {

                            h4 { +"Edit Character ${data.character.fullName}" }

                            form(method = FormMethod.post) {

                                form_group {
                                    row {
                                        col_md_3 {
                                            textInput(t, form.name, label = "Name")
                                        }
                                        col_md_3 {
                                            textInput(t, form.surname, label = "Surname")
                                        }
                                        col_md_3 {
                                            textInput(t, form.age, label = "Age")
                                        }
                                        col_md_3 {
                                            selectInput(t, form.alive, label = "Alive")
                                        }
                                    }
                                }

                                // TODO: fixme
//                                form.actor?.let { actorForm ->
//                                    h4 { +"Edit Actor ${data.character.actor?.value?.name}" }
//
//                                    row {
//                                        col_md_3 {
//                                            textInput(t, actorForm.name, label = "Name")
//                                        }
//
//                                        col_md_3 {
//                                            numberInput(t, actorForm.age, label = "Age")
//                                        }
//                                    }
//                                }

                                submit { +"Submit" }
                            }
                        }
                    }
                }
            }
        }
    }
}


