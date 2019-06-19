package de.peekandpoke.module.got

import com.fasterxml.jackson.databind.ObjectMapper
import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import de.peekandpoke.common.*
import de.peekandpoke.formidable.render
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.aql.ASC
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.examples.game_of_thrones.*
import de.peekandpoke.ultra.common.md5
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.utils.EmptyContent
import io.ktor.html.respondHtml
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import kotlinx.html.*
import kotlinx.html.label
import kotlinx.html.FormMethod.post as post1


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

    @Location("/bexio")
    internal class Bexio

    @Location("/characters")
    internal class GetCharacters(val page: Int = 1, val epp: Int = 100)

    @Location("/characters/{character}")
    internal class GetCharacter(val character: Character)

    @Location("/forms")
    internal class FormTest

    inner class LinkTo : LinkGenerator(mountPoint) {
        fun getCharacters() = linkTo(GetCharacters())
        fun getCharacterByKey(character: Character) = linkTo(GetCharacter(character))
    }

    val linkTo = LinkTo()

    init {

        with(mountPoint) {

            get<Bexio> {

                val company = "apehctwyu7vz"
                val user = "1"
                val publicKey = "1423abd5e3a043f4347e313bec4c5abb"
                val signatureKey = "b0f0a94720b24604c794dc6393184b56"


                val base = "https://office.bexio.com/api2.php/$company/$user/$publicKey"


                val client = HttpClient(CIO) {

                    install(JsonFeature) {
                        serializer = JacksonSerializer()
                    }

                    defaultRequest {
                        // accept(ContentType.Application.Json)

                        val signature = when (body) {
                            is EmptyContent -> "${method.value.toLowerCase()}${url.build()}$signatureKey"

                            is String -> "${method.value.toLowerCase()}${url.build()}$body$signatureKey"

                            else -> {
                                throw Exception("Cannot calculate signature for ${body::class} $body")
                            }
                        }

                        println(signature)
                        println(signature.md5())

                        header("Signature", signature.md5())
                    }
                }

                val mapper = ObjectMapper()

                val order = client.get<Map<String, Any>>("$base/kb_order/1")
                val text = client.post<Map<*, *>>("$base/kb_order/2/kb_position_text/3") {
                    body = mapper.writeValueAsString(
                        mapOf("text" to "HalliHallo - 2")
                    )
                }

                call.respond(
                    mapOf(
                        "order" to order,
                        "text" to text
                    )
                )
            }

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

                    head {
                        link(rel = "stylesheet", href = "/assets/bootstrap/css/bootstrap.css")
                    }

                    body {
                        container_fluid {
                            h4 { +"Characters" }

                            ul {
                                list.forEach {
                                    li {
                                        a(href = linkTo.getCharacterByKey(it)) { +"${it.name} ${it.surname ?: ""}" }

                                        it.age?.let {
                                            span { +" Age: $it" }
                                        }

                                        it.actor?.let {
                                            span { +" (Actor: ${it.name} ${it.surname})" }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            getOrPost<GetCharacter> {

                val mutator = it.character.mutator()
                val form = CharacterForm(mutator)

                if (form.submit(call)) {

                    println(mutator.getResult())

                    val saved = characters.save(mutator.getResult())

                    logger.info("Updated character in database: $saved")

                    call.respondRedirect(linkTo.getCharacters())

                } else {

                    call.respondHtml {

                        head {
                            link(rel = "stylesheet", href = "/assets/bootstrap/css/bootstrap.css")
                        }

                        body {

                            container {

                                h4 { +"Edit Character ${it.character.fullName}" }

                                form(method = FormMethod.post) {

                                    form_group {
                                        row {
                                            col_md_3 {
                                                render(form.name, label = "Name")
                                            }
                                            col_md_3 {
                                                render(form.surname, label = "Surname")
                                            }
                                            col_md_3 {
                                                render(form.age, label = "Age")
                                            }
                                        }

                                    }

                                    form.actor?.let { actorForm ->
                                        h4 { +"Edit Actor ${it.character.actor?.name}" }

                                        row {
                                            col_md_3 {
                                                render(actorForm.name, label = "Name")
                                            }

                                            col_md_3 {
                                                render(actorForm.age, label = "Age")
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
}


