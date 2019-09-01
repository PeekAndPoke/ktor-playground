package de.peekandpoke

import com.fasterxml.jackson.databind.SerializationFeature
import de.peekandpoke.common.logger
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.examples.game_of_thrones.Character
import de.peekandpoke.karango.examples.game_of_thrones.Characters
import de.peekandpoke.karango_ktor.add
import de.peekandpoke.module.got.gameOfThrones
import de.peekandpoke.resources.MainTemplate
import de.peekandpoke.resources.Translations
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.html.respondHtml
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.*
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.http.content.CachingOptions
import io.ktor.jackson.jackson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.request.header
import io.ktor.request.receive
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.host
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.sessions.*
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.getDigestFunction
import io.ktor.util.hex
import io.ktor.websocket.webSocket
import io.ultra.ktor_tools.FlashSession
import io.ultra.ktor_tools.resources.*
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.*
import java.time.Duration
import java.util.*
import kotlin.collections.set
import kotlin.system.measureNanoTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

private val db = Db.default(user = "root", pass = "", host = "localhost", port = 8529, database = "kotlindev")

val Meta = object : AppMeta() {}

val WebResources = webResources(Meta) {

    group("legacy") {

        webjarCss("/vendor/bootstrap/css/bootstrap.css")
        webjarJs("/vendor/bootstrap/js/bootstrap.min.js")

        webjarCss("/vendor/font-awesome/css/all.css")

        webjarJs("/vendor/jquery/jquery.min.js")

        // custom
        resourceCss("/assets/css/styles.css")
        resourceJs("/assets/js/template.js")
    }

    group("semantic") {
        webjarCss("/vendor/Semantic-UI/semantic.css")
        webjarJs("/vendor/Semantic-UI/semantic.js")
    }
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Locations) {
    }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
            cookie.path = "/"
            transform(SessionTransportTransformerMessageAuthentication(hex("abcdefg")))
        }

        FlashSession.register(this)
    }

    val authFeature = install(Authentication) {
        basic("myBasicAuth") {
            realm = "Ktor Server"
            validate { if (it.name == "test" && it.password == "password") UserIdPrincipal(it.name) else null }
        }
    }

    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    install(AutoHeadResponse)

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to LIMIT it.
    }

    install(DataConversion) {
        add(db, Characters, Character::class)
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(HSTS) {
        includeSubDomains = true
    }

    install(BetterWebjars) {
        loader = Application::class.java.classLoader
    }

    install(io.ktor.websocket.WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    install(PartialContent) {
        // Maximum number of ranges that will be accepted from a HTTP request.
        // If the HTTP request specifies more ranges, they will all be merged into a single range.
        maxRangeCount = 10
    }

//    install(Authentication) {
//        basic("myBasicAuth") {
//            realm = "Ktor Server"
//            validate { if (it.name == "test" && it.password == "password") UserIdPrincipal(it.name) else null }
//        }
//    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(CachingHeaders) {
        options { outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {

                ContentType.Text.CSS ->
                    CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 30 * 24 * 60 * 60))

                ContentType.Application.JavaScript ->
                    CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 30 * 24 * 60 * 60))

                else -> null
            }
        }
    }

    install(StatusPages) {

        exception<Throwable> { cause ->
            cause.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
        }

        exception<AuthenticationException> {
            call.respond(HttpStatusCode.Unauthorized)
        }

        exception<AuthorizationException> {
            call.respond(HttpStatusCode.Forbidden)
        }

        exception<BadRequestException> {
            call.respond(HttpStatusCode.InternalServerError, "Bad Request ...")
        }

        exception<ParameterConversionException> { cause ->

            call.respondHtml(HttpStatusCode.NotFound) {
                body {
                    div {
                        +"Not found"
                    }
                    div {
                        +cause.toString()
                    }
                }
            }
        }

        status(HttpStatusCode.NotFound) { cause ->

            call.respondHtml(HttpStatusCode.NotFound) {
                body {
                    div {
                        +"Not found"
                    }
                    div {
                        +cause.toString()
                    }
                }
            }
        }
    }

    intercept(ApplicationCallPipeline.Setup) {

        val ns = measureNanoTime { proceed() }

        logger.info("... total ${ns / 1_000_000.0} ms")
    }

    intercept(ApplicationCallPipeline.Features) {

        val requestId = UUID.randomUUID()

        logger.attach("req.Id", requestId.toString()) {
            logger.info("Feature[start]")

            val ns = measureNanoTime {
                proceed()
            }

            logger.info("Feature[end] ${ns / 1_000_000.0} ms")
        }
    }

    intercept(ApplicationCallPipeline.Features) {

        logger.info("injecting services")

        with(call.attributes) {

            put(Translations.withLocale("en"))
            put(WebResources)
        }
    }

    routing {

        val digester = getDigestFunction("SHA-256") { "ktor${it.length}" }

        // We have a single user for testing in the user table: user=root, password=root
        // So for the login you have to use those credentials since you cannot register new users in this sample.
        val users = UserHashedTableAuth(
            digester,
            table = mapOf(
                "root" to digester("root"),
                "rudi" to digester("rudi")
            )
        )

        login(authFeature, users)

        get("/assets/{path...}") {

            val filename = call.request.uri.split("?").first()

            logger.info("ASSET: $filename")

            val stream = Application::class.java.getResourceAsStream(filename) ?: throw NotFoundException()

            call.respondText(String(stream.readBytes()), ContentType.Text.CSS)
        }

        webSocket("/myws/echo") {
            send(Frame.Text("Hi from server"))
            while (true) {
                val frame = incoming.receive()
                if (frame is Frame.Text) {
                    send(Frame.Text("Client said: " + frame.readText()))
                }
            }
        }

        authenticate("myBasicAuth") {
            get("/protected/route/basic") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }

        val gameOfThrones = gameOfThrones()

        get("/") {

            call.respondHtmlTemplate(MainTemplate(call)) {}

            val name = call.sessions.get<MySession>()?.userId ?: "Stranger"

            call.respondText("HELLO $name!", contentType = ContentType.Text.Plain)
        }

        get("/__hello__") {
            call.respondText("Hello", ContentType.Text.Html, HttpStatusCode.OK)
        }

        get("/semantic-ui") {
            call.respondHtml {

                head {

                    meta {
                        charset = "utf-8"
                    }

                    iocWebResources["semantic"].css.forEach { css ->
                        link(rel = "stylesheet", href = css.fullUri) {
                            css.integrity?.let { integrity = it }
                        }
                    }
                }

                body {

                    ui.container {

                        h1 {
                            +"Site"
                        }

                        ui.three.column.stackable.divided.grid {

                            ui.column {
                                h1 { +"Heading 1" }
                                h2 { +"Heading 2" }
                                h3 { +"Heading 3" }
                                h4 { +"Heading 4" }
                                h5 { +"Heading 5" }
                                h6 { +"Heading 6" }
                            }

                            ui.column {
                                h2 { +"""Example body text""" }
                                p {
                                    +"""Nullam quis risus eget"""
                                    a {
                                        href = "#"
                                        +"""urna mollis ornare"""
                                    }
                                    +"""vel eu leo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam id dolor id nibh ultricies vehicula."""
                                }
                                p {
                                    small { +"""This line of text is meant to be treated as fine print.""" }
                                }
                                p {
                                    +"""The following snippet of text is """
                                    strong { +"""rendered as bold text""" }
                                    +"""."""
                                }
                                p {
                                    +"""The following snippet of text is """
                                    em { +"""rendered as italicized text""" }
                                    +"""."""
                                }
                                p {
                                    +"""An abbreviation of the word attribute is """
                                    abbr {
                                        title = "attribute"
                                        +"""attr"""
                                    }
                                    +"""."""
                                }
                            }

                            ui.column {
                                ui.three.column.stackable.padded.middle.aligned.centered.column.grid {

                                    ui.red.column { +"Red" }
                                    ui.orange.column { +"Orange" }
                                    ui.yellow.column { +"Yellow" }
                                    ui.olive.column { +"olive" }
                                    ui.green.column { +"green" }
                                    ui.teal.column { +"teal" }
                                    ui.blue.column { +"blue" }
                                    ui.violet.column { +"violet" }
                                    ui.purple.column { +"purple" }
                                    ui.pink.column { +"pink" }
                                    ui.brown.column { +"brown" }
                                    ui.grey.column { +"grey" }
                                    ui.black.column { +"black" }
                                }
                            }
                        }
                    }
                }
            }
        }

        host("admin.*".toRegex()) {
            get("/admin") {
                call.respondHtml {
                    body {
                        div {
                            +"Admin area"
                        }
                    }
                }
            }
        }

        authenticate("myFormAuthentication") {

            get("/session/increment") {
                val session = call.sessions.get<MySession>()!!
                call.sessions.set(session.copy(count = session.count + 1))
                call.respondText("Counter is ${session.count}. Refresh to increment.")
            }

            get("/private/show") {

                logger.info("Private[start]")

                call.respondHtml {
                    body {
                        div { +("Private show " + call.sessions.get<MySession>()!!.userId) }
                        div {
                            a(href = "/logout") { +"Logout" }
                        }
                        div {
                            attributes["data-stuff"] = "stuff"
                            a(href = gameOfThrones.linkTo.getCharacters()) { +"GoT" }
                        }
                        div {
                            +(call.request.header(HttpHeaders.Host) ?: "n/a")
                        }
                        form(method = FormMethod.post) {
                            div {
                                label { +"Name" }
                                textInput { name = "input[name]" }
                            }
                            div {
                                label { +"Age" }
                                numberInput { name = "input[age]" }
                            }
                            div {
                                submitInput { +"submit" }
                            }
                        }
                    }
                }

                logger.info("Private[end]")
            }

            post("/private/show") {
                logger.info("Private[post]")
                call.respondText {
                    call.receive()
                }
            }
        }
    }
}

data class MySession(val userId: String? = null, val count: Int = 0)

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
