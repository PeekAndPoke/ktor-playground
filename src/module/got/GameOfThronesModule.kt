package de.peekandpoke.module.got

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import de.peekandpoke.common.LinkGenerator
import de.peekandpoke.common.logger
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.aql.ASC
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.examples.game_of_thrones.Character
import de.peekandpoke.karango.examples.game_of_thrones.Characters
import de.peekandpoke.karango.examples.game_of_thrones.name
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import kotlinx.html.*


object GameOfThronesSpec : ConfigSpec("gameOfThrones") {
    val mountPoint by required<String>()
}

class GameOfThronesConfig(private val config: Config) {
    val mountPoint get() = config[GameOfThronesSpec.mountPoint]
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.gameOfThrones(db: Db): GameOfThronesModule {

    val config = GameOfThronesConfig(
        Config { addSpec(GameOfThronesSpec) }.from.hocon.resource("module.game-of-thrones.conf")
    )

    @KtorExperimentalLocationsAPI
    lateinit var module: GameOfThronesModule

    routing {
        route(config.mountPoint) {
            module = GameOfThronesModule(this, config, db)
        }
    }

    return module
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class GameOfThronesModule(val mountPoint: Route, val config: GameOfThronesConfig, db: Db) {

    @Location("/characters")
    internal class GetCharacters(val page: Int = 1, val epp: Int = 100)

    @Location("/characters/{character}")
    internal class GetCharacter(val character: Character)

    @Location("/forms")
    internal class FormTest()

    inner class LinkTo : LinkGenerator(mountPoint) {
        fun getCharacters() = linkTo(GetCharacters())
        fun getCharacterByKey(character: Character) = linkTo(GetCharacter(character))
    }

    val linkTo = LinkTo()

    init {
        with(mountPoint) {

            get<FormTest> {

                call.respondHtml {
                    body {
                        form(method = FormMethod.post) {
                            label {
                                +"Name"
                                input(name = "name")
                            }
                            label {
                                +"Name again"
                                input(name = "name")
                            }

                            button(type = ButtonType.submit) {
                                +"Submit"
                            }
                        }
                    }
                }
            }

            post<FormTest> {

                val posted = call.receive<Parameters>()

                call.respond(posted.entries())
            }

            get<GetCharacters> { p ->

                val result = db.query {
                    FOR(Characters) { c ->
                        SORT(c.name.ASC)
                        LIMIT((p.page - 1) * p.epp, p.epp)
                        RETURN(c)
                    }
                }

                logger.info("${result.timeMs} vs ${result.stats.executionTime}")

                val list = result.toList()

                call.respondHtml {
                    body {
                        h4 { +"Characters" }
                        ul {
                            list.forEach {
                                li {
                                    a(href = linkTo.getCharacterByKey(it)) { +"${it.name} ${it.surname}" }

                                    it.actor?.let {
                                        span { +"(Actor: ${it.name} ${it.surname})" }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            get<GetCharacter> {
                call.respond(it.character)
            }
        }
    }
}


