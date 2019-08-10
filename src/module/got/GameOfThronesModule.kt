package de.peekandpoke.module.got

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import de.peekandpoke.common.logger
import de.peekandpoke.karango.examples.game_of_thrones.*
import de.peekandpoke.resources.MainTemplate
import de.peekandpoke.resources.WELCOME
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.architecture.LinkGenerator
import io.ultra.ktor_tools.bootstrap.*
import io.ultra.ktor_tools.flashSession
import io.ultra.ktor_tools.getOrPost
import kotlinx.html.*

object GameOfThronesSpec : ConfigSpec("gameOfThrones") {
    val mountPoint by required<String>()
}

class GameOfThronesConfig(private val config: Config) {
    val mountPoint get() = config[GameOfThronesSpec.mountPoint]
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.gameOfThrones(): GameOfThronesModule {

    val config = GameOfThronesConfig(
        Config { addSpec(GameOfThronesSpec) }.from.hocon.resource("module.game-of-thrones.conf")
    )

    @KtorExperimentalLocationsAPI
    lateinit var module: GameOfThronesModule

    routing {
        route(config.mountPoint) {
            module = GameOfThronesModule(this, config)
        }
    }

    return module
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class GameOfThronesModule(val mountPoint: Route, val config: GameOfThronesConfig) {

    @Location("/characters")
    internal class GetCharacters(val page: Int = 1, val epp: Int = 100)

    @Location("/characters/{character}")
    internal class GetCharacter(val character: Character)

    inner class LinkTo : LinkGenerator(mountPoint) {
        fun getCharacters() = linkTo(GetCharacters())
        fun getCharacterByKey(character: Character) = linkTo(GetCharacter(character))
    }

    val linkTo = LinkTo()

    init {

        with(mountPoint) {

            get<GetCharacters> { p ->

                val result = Characters.findAllPaged(p.page, p.epp)

                logger.info("${result.timeMs} vs ${result.stats.executionTime}")

                val list = result.toList()

                val flashEntries = flashSession.pull()

                call.respondHtmlTemplate(MainTemplate(call)) {

                    content {
                        container_fluid {

                            flashEntries.takeIf { it.isNotEmpty() }?.let { entries ->
                                div {
                                    entries.forEach { entry -> div(classes = "alert alert-${entry.type}") { +entry.message } }
                                }
                            }

                            h2 { +t.WELCOME() }

                            h4 { +"Characters" }

                            ul {
                                list.forEach {

                                    li {
                                        a(href = linkTo.getCharacterByKey(it)) { +"${it.name} ${it.surname ?: ""} " }

                                        span { +"${if (it.alive) "alive" else "dead"} " }

                                        it.age?.let { span { +"Age: $it " } }

                                        it.actor?.let { span { +"(Actor: ${it.name} ${it.surname} Age: ${it.age}) " } }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            getOrPost<GetCharacter> { data ->

                val form = CharacterForm(data.character.mutator())

                if (form.submit(call)) {

                    if (form.isModified) {

                        val savedActor = form.result.actor?.let { actors.save(it) }
                        val saved = characters.save(form.result)

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

                                form.actor?.let { actorForm ->
                                    h4 { +"Edit Actor ${data.character.actor?.name}" }

                                    row {
                                        col_md_3 {
                                            textInput(t, actorForm.name, label = "Name")
                                        }

                                        col_md_3 {
                                            numberInput(t, actorForm.age, label = "Age")
                                        }
                                    }
                                }

                                submit { +"Submit" }
                            }
                        }
                    }
                }
            }
        }
    }
}


