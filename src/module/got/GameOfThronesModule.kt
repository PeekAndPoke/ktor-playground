package de.peekandpoke.module.got

import com.fasterxml.jackson.databind.ObjectMapper
import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import de.peekandpoke.common.LinkGenerator
import de.peekandpoke.common.getOrPost
import de.peekandpoke.common.logger
import de.peekandpoke.formidable.Form
import de.peekandpoke.formidable.field
import de.peekandpoke.formidable.textInput
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
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.httpMethod
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import kotlinx.html.*
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

            getOrPost<GetCharacter> {

                val mutator = it.character.mutator()
                val form = object : Form() {
                    val name = textInput(mutator::name)
                    val surname = textInput(mutator::surname)
                }


                if (call.request.httpMethod == HttpMethod.Post) {

                    val posted = call.receive<Parameters>()

                    logger.info(posted.toString())

                    form.submit(posted)

//                    val result = it.character.mutate {
//
//                        posted["name"]?.let { name = it }
//                        posted["surname"]?.let { surname = it }
//                    }

                    val saved = characters.save(mutator.getResult())

                    logger.info("Updated character in database: $saved")

                    call.respondRedirect(linkTo.getCharacters())

                } else {

                    call.respondHtml {
                        body {
                            h4 { +"Edit Character ${it.character.fullName}" }

                            form(method = FormMethod.post) {
                                label {
                                    +"Name"
//                                    textInput(name = "name") { attributes["value"] = it.character.name }
                                    field(form.name)
                                }
                                label {
                                    +"Surname"
//                                    textInput(name = "surname") { attributes["value"] = (it.character.surname ?: "") }
                                    field(form.surname)
                                }
                                submitInput { +"Submit" }
                            }
                        }
                    }
                }
            }
        }
    }
}


