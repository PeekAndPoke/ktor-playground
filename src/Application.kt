package de.peekandpoke

import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import com.fasterxml.jackson.databind.SerializationFeature
import de.peekandpoke.karango.KarangoDriver
import de.peekandpoke.module.cms.CmsAdmin
import de.peekandpoke.module.cms.CmsAdminModule
import de.peekandpoke.module.cms.CmsPublic
import de.peekandpoke.module.cms.CmsPublicModule
import de.peekandpoke.module.got.GameOfThrones
import de.peekandpoke.module.got.GameOfThronesModule
import de.peekandpoke.module.semanticui.SemanticUi
import de.peekandpoke.module.semanticui.SemanticUiModule
import de.peekandpoke.resources.Translations
import de.peekandpoke.ultra.kontainer.kontainer
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.DefaultEntityCache
import de.peekandpoke.ultra.vault.EntityCache
import de.peekandpoke.ultra.vault.NullEntityCache
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.html.respondHtml
import io.ktor.http.*
import io.ktor.http.content.CachingOptions
import io.ktor.http.content.resource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.request.httpMethod
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.host
import io.ktor.routing.routing
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.getDigestFunction
import io.ktor.util.hex
import io.ultra.ktor_tools.FlashSession
import io.ultra.ktor_tools.KontainerKey
import io.ultra.ktor_tools.KtorFX
import io.ultra.ktor_tools.logger.logger
import io.ultra.ktor_tools.provide
import io.ultra.ktor_tools.resources.AppMeta
import io.ultra.ktor_tools.resources.BetterWebjars
import io.ultra.ktor_tools.resources.webResources
import io.ultra.polyglot.I18n
import kotlinx.html.*
import java.time.Duration
import java.util.*
import kotlin.collections.set
import kotlin.system.measureNanoTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

private val arangoDb: ArangoDB = ArangoDB.Builder().user("root").password("").host("localhost", 8529).build()

private val arangoDatabase: ArangoDatabase = arangoDb.db("kotlindev")

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


val kontainerBlueprint = kontainer {

    // functionality modules /////////////////////////////////////////////////////////////////////////////////////////////

    module(KtorFX)
    instance(WebResources) // We re-define the web resource
    dynamic(I18n::class)   // Redefine the i18n as a dynamic service, so we are forced to inject it with user language

    // database drivers

    instance(arangoDatabase)
    singleton(KarangoDriver::class)
    dynamic(EntityCache::class)

    // application modules ///////////////////////////////////////////////////////////////////////////////////////////////

    module(GameOfThronesModule)

    module(SemanticUiModule)

    module(CmsAdminModule)
    module(CmsPublicModule)
}

fun initKontainer() = kontainerBlueprint.useWith(
    // default language
    Translations.withLocale("en"),
    // Entity cache
    NullEntityCache()
)

fun requestContainer() = kontainerBlueprint.useWith(
    // default language
    Translations.withLocale("en"),
    // Entity cache
    DefaultEntityCache()
)

@KtorExperimentalAPI
@kotlin.jvm.JvmOverloads
@Suppress("unused", "UNUSED_PARAMETER") // Referenced in application.conf
fun Application.module(testing: Boolean = false) {

    val initKontainer = initKontainer()

    // Ensure all database repositories are set up properly
    initKontainer.get(Database::class).ensureRepositories()

    // Get all application modules
    val gameOfThrones = initKontainer.get(GameOfThrones::class)

    val semanticUi = initKontainer.get(SemanticUi::class)

    val cmsAdmin = initKontainer.get(CmsAdmin::class)
    val cmsPublic = initKontainer.get(CmsPublic::class)

    environment.monitor.subscribe(ApplicationStopped) {
        arangoDb.shutdown()
    }

    install(Sessions) {
        cookie<UserSession>("user") {
            cookie.extensions["SameSite"] = "lax"
            cookie.path = "/"
            transform(SessionTransportTransformerMessageAuthentication(hex("abcdefg")))
        }

        cookie<LoginSession>("login") {
            cookie.extensions["SameSite"] = "lax"
            cookie.path = "/"
            transform(SessionTransportTransformerMessageAuthentication(hex("abcdefg")))
        }

        FlashSession.register(this)
    }

    install(Authentication) {
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

    install(DefaultHeaders) {
        //        header("X-Engine", "Ktor") // will send this header with each response
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

                // f.e. favicon
                ContentType.Image.XIcon ->
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

        logger.debug("${call.request.httpMethod.value} ${call.request.uri} took ${ns / 1_000_000.0} ms")
    }

    intercept(ApplicationCallPipeline.Features) {

        val requestId = UUID.randomUUID()

        logger.attach("req.Id", requestId.toString()) {
            proceed()
        }

        logger.debug(call.attributes[KontainerKey].dump())
    }

    intercept(ApplicationCallPipeline.Features) {

        val ns = measureNanoTime {

            logger.info(call.attributes.allKeys.toString())

            // via the calls attributes we provide the following things
            with(call.attributes) {
                // inject a fresh Kontainer into each call
                provide(requestContainer())
            }
        }

        logger.debug("Service injection into call attributes took ${ns / 1_000_000.0} ms")
    }

    routing {

        //        trace { application.log.trace(it.buildText()) }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  Common area
        /////

        static {
            resource("favicon.ico", resourcePackage = "assets")
        }

        static("assets") {
            resources("assets")
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  Frontend area
        /////

        host("www.*".toRegex()) {
            gameOfThrones.mount(this)
            cmsPublic.mount(this)
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  Admin area
        /////

        host("admin.*".toRegex()) {

            val authName = "adminAreaFormAuth"

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

            login(authName, users)

            get("/") {
                call.respondHtml {
                    body {
                        h1 {
                            +"Admin area"
                        }
                        ul {
                            li {
                                a(href = semanticUi.routes.index) { +"Semantic UI demo" }
                            }
                            li {
                                a(href = cmsAdmin.routes.index) { +"Mini CMS" }
                            }
                        }
                    }
                }
            }

            authenticate(authName) {
                semanticUi.mount(this)
                cmsAdmin.mount(this)
            }
        }
    }
}

data class LoginSession(val requestedUri: String)

data class UserSession(val userId: String? = null, val count: Int = 0)

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
