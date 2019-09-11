package de.peekandpoke

import com.fasterxml.jackson.databind.SerializationFeature
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.examples.game_of_thrones.registerGotCollections
import de.peekandpoke.karango_ktor.add
import de.peekandpoke.module.cms.cmsAdmin
import de.peekandpoke.module.cms.cmsPublic
import de.peekandpoke.module.cms.registerCmsCollections
import de.peekandpoke.module.got.gameOfThrones
import de.peekandpoke.module.semanticui.semanticUi
import de.peekandpoke.resources.Translations
import de.peekandpoke.test_module.TestModule
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.html.respondHtml
import io.ktor.http.*
import io.ktor.http.content.CachingOptions
import io.ktor.jackson.jackson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.host
import io.ktor.routing.routing
import io.ktor.sessions.*
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.getDigestFunction
import io.ktor.util.hex
import io.ultra.ktor_tools.FlashSession
import io.ultra.ktor_tools.logger.logger
import io.ultra.ktor_tools.resources.AppMeta
import io.ultra.ktor_tools.resources.BetterWebjars
import io.ultra.ktor_tools.resources.put
import io.ultra.ktor_tools.resources.webResources
import kotlinx.html.*
import java.time.Duration
import java.util.*
import kotlin.collections.set
import kotlin.system.measureNanoTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

private val db: Db = Db.default(user = "root", pass = "", host = "localhost", port = 8529, database = "kotlindev").apply {
    registerCmsCollections()
    registerGotCollections()
}

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

        webjarJs("/vendor/jquery/jquery.min.js")
        webjarJs("/vendor/Semantic-UI/semantic.js")
    }

    group("prism") {
        webjarCss("/vendor/prismjs/prism.css")
        webjarCss("/vendor/prismjs/prism.css")
        webjarCss("/vendor/prismjs/plugins/toolbar/prism-toolbar.css")

        webjarJs("/vendor/prismjs/prism.js")
        webjarJs("/vendor/prismjs/plugins/toolbar/prism-toolbar.js")
        webjarJs("/vendor/prismjs/show-language/prism-show-language.js")
        webjarJs("/vendor/prismjs/components/prism-kotlin.js")
    }
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@kotlin.jvm.JvmOverloads
@Suppress("unused", "UNUSED_PARAMETER") // Referenced in application.conf
fun Application.module(testing: Boolean = false) {

    val gameOfThronesModule = gameOfThrones(db)

    val semanticUiModule = semanticUi()

    val cmsAdminModule = cmsAdmin(db)
    val cmsPublicModule = cmsPublic(db)

    install(Locations) {
    }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
            cookie.path = "/"
            transform(SessionTransportTransformerMessageAuthentication(hex("abcdefg")))
        }

        cookie<LoginSession>("LOGIN") {
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
        add(db)
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

                // f.e. woff2 files
                ContentType.Application.OctetStream ->
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

        trace { application.log.trace(it.buildText()) }

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

        get("/assets/{path...}") {

            val filename = call.request.uri.split("?").first()

            logger.info("ASSET: $filename")

            val stream = Application::class.java.getResourceAsStream(filename) ?: throw NotFoundException()

            call.respondText(String(stream.readBytes()), ContentType.Text.CSS)
        }

        authenticate("myBasicAuth") {
            get("/protected/route/basic") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }

        get("/") {

            val name = call.sessions.get<MySession>()?.userId ?: "Stranger"

            call.respondText("HELLO $name!", contentType = ContentType.Text.Plain)
        }

        get("/test") {
            call.respondText(TestModule().doSomething(111).toString(), ContentType.Text.Html, HttpStatusCode.OK)
        }

        gameOfThronesModule.mount(this)
        cmsPublicModule.mount(this)

        host("admin.*".toRegex()) {

            login(authFeature, users)

            get("/") {
                call.respondHtml {
                    body {
                        h1 {
                            +"Admin area"
                        }
                        ul {
                            li {
                                a(href = semanticUiModule.linkTo.index()) { +"Semantic UI demo" }
                            }
                            li {
                                a(href = cmsAdminModule.linkTo.index()) { +"Mini CMS" }
                            }
                        }
                    }
                }
            }

            authenticate("myFormAuthentication") {

                semanticUiModule.mount(this)
                cmsAdminModule.mount(this)

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
        }
    }
}

data class LoginSession(val requestedUri: String)

data class MySession(val userId: String? = null, val count: Int = 0)

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
