package de.peekandpoke.module.got

import com.fasterxml.jackson.databind.ObjectMapper
import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import de.peekandpoke.common.logger
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.aql.ASC
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.examples.game_of_thrones.*
import de.peekandpoke.resources.MainTemplate
import de.peekandpoke.resources.WELCOME
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
import io.ktor.html.respondHtmlTemplate
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
import io.ultra.common.md5
import io.ultra.ktor_tools.architecture.LinkGenerator
import io.ultra.ktor_tools.bootstrap.*
import io.ultra.ktor_tools.flashSession
import io.ultra.ktor_tools.getOrPost
import kotlinx.html.*
import kotlinx.html.label


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
                        container_fluid  {

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


