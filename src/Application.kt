package de.peekandpoke

import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import com.fasterxml.jackson.databind.SerializationFeature
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ktorfx.webjars.BetterWebjars
import de.peekandpoke.ktorfx.webresources.AppMeta
import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
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
import de.peekandpoke.ultra.vault.hooks.StaticUserRecordProvider
import de.peekandpoke.ultra.vault.hooks.UserRecord
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
import io.ktor.sessions.*
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.getDigestFunction
import io.ktor.util.hex
import io.ultra.ktor_tools.FlashSession
import io.ultra.ktor_tools.KontainerKey
import io.ultra.ktor_tools.KtorFX
import io.ultra.ktor_tools.logger.logger
import io.ultra.ktor_tools.provide
import io.ultra.polyglot.I18n
import kotlinx.html.*
import java.net.InetAddress
import java.time.Duration
import java.util.*
import kotlin.collections.set
import kotlin.system.measureNanoTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

private val arangoDb: ArangoDB = ArangoDB.Builder().user("root").password("").host("localhost", 8529).build()

private val arangoDatabase: ArangoDatabase = arangoDb.db("kotlindev")

val Meta = object : AppMeta() {}

class LegacyWebResources(cacheBuster: CacheBuster) : WebResourceGroup(cacheBuster, {
    webjarJs("/vendor/jquery/jquery.min.js")

    // custom
    resourceCss("/assets/css/styles.css")
    resourceJs("/assets/js/template.js")
})

class PrismJsWebResources(cacheBuster: CacheBuster) : WebResourceGroup(cacheBuster, {
    webjarCss("/vendor/prismjs/prism.css")
    webjarCss("/vendor/prismjs/prism.css")
    webjarCss("/vendor/prismjs/plugins/toolbar/prism-toolbar.css")

    webjarJs("/vendor/prismjs/prism.js")
    webjarJs("/vendor/prismjs/plugins/toolbar/prism-toolbar.js")
    webjarJs("/vendor/prismjs/show-language/prism-show-language.js")
    webjarJs("/vendor/prismjs/components/prism-kotlin.js")
})

val kontainerBlueprint = kontainer {

    // functionality modules /////////////////////////////////////////////////////////////////////////////////////////////

    module(KtorFX)

    // database drivers

    instance(arangoDatabase)
    singleton(KarangoDriver::class)

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  APPLICATION  /////////////////////////////////////////////////////////////////////////////////////////////////////

    // We re-define the cache buster, so we can read the version of the application and use it as cache buster key
    instance(Meta.cacheBuster())
    // We re-define the i18n as a dynamic service, so we can inject it with user language for each request
    dynamic(I18n::class) { Translations.withLocale("en") }

    // application web resources

    singleton(LegacyWebResources::class)
    singleton(PrismJsWebResources::class)

    // application modules

    module(GameOfThronesModule)

    module(SemanticUiModule)

    module(CmsAdminModule)
    module(CmsPublicModule)
}

fun systemKontainer() = kontainerBlueprint.useWith(
    // user record provider
    StaticUserRecordProvider(
        UserRecord("system", InetAddress.getLocalHost().canonicalHostName)
    )
)

fun requestContainer(user: UserRecord) = kontainerBlueprint.useWith(
    // default language
    Translations.withLocale("en"),
    // user record provider
    StaticUserRecordProvider(user)
)

@KtorExperimentalAPI
@kotlin.jvm.JvmOverloads
@Suppress("unused", "UNUSED_PARAMETER") // Referenced in application.conf
fun Application.module(testing: Boolean = false) {

    val initKontainer = systemKontainer()

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

            with(call.attributes) {
                // inject a fresh Kontainer into each call
                provide(
                    requestContainer(
                        UserRecord(
                            call.sessions.get<UserSession>()?.userId ?: "anonymous",
                            call.request.origin.remoteHost
                        )
                    )
                )
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
