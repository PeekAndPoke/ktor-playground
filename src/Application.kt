package de.peekandpoke

import com.fasterxml.jackson.databind.SerializationFeature
import de.peekandpoke.karango.KarangoModule
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.common.provide
import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.ktorfx.webjars.BetterWebjars
import de.peekandpoke.ktorfx.webresources.AppMeta
import de.peekandpoke.module.cms.CmsAdmin
import de.peekandpoke.module.cms.CmsAdminModule
import de.peekandpoke.module.cms.CmsPublic
import de.peekandpoke.module.cms.CmsPublicModule
import de.peekandpoke.module.got.GameOfThrones
import de.peekandpoke.module.got.GameOfThronesModule
import de.peekandpoke.module.semanticui.SemanticUi
import de.peekandpoke.module.semanticui.SemanticUiModule
import de.peekandpoke.resources.Translations
import de.peekandpoke.ultra.kontainer.Kontainer
import de.peekandpoke.ultra.kontainer.kontainer
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.Vault
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
import io.ultra.ktor_tools.KtorFX
import io.ultra.ktor_tools.logger.logger
import io.ultra.polyglot.I18n
import kotlinx.html.*
import java.net.InetAddress
import java.time.Duration
import java.util.*
import kotlin.collections.set
import kotlin.system.measureNanoTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


val AppMeta = object : AppMeta() {}

val Application.kontainerBlueprint by lazy {

    kontainer {

        // Database //////////////////////////////////////////////////////////////////////////////////////////////////////////
        module(Vault)
        module(KarangoModule)
        // provide connection to arango database
        instance(arangoDatabase)

        // import ALL of KtorFX
        module(KtorFX)
        // create a app specific cache buster
        instance(AppMeta.cacheBuster())
        // Re-define the I18n with our texts
        // TODO we a way to omit the first parameter
        dynamic(I18n::class) { _: Kontainer -> Translations.withLocale("en") }

        // set default html template
        // TODO: we need a solution to divide Frontend from Backend
        //       -> idea: KontainerBlueprint.extend(KontainerBuilder)) ?
        prototype(SimpleTemplate::class, AdminTemplate::class)


        // application ///////////////////////////////////////////////////////////////////////////////////////////////////////


        // application modules
        module(GameOfThronesModule)
        module(SemanticUiModule)
        module(CmsAdminModule)
        module(CmsPublicModule)
    }
}

fun Application.systemKontainer() = kontainerBlueprint.useWith(
    // user record provider
    StaticUserRecordProvider(
        UserRecord("system", InetAddress.getLocalHost().canonicalHostName)
    )
)

fun Application.requestContainer(user: UserRecord, session: CurrentSession) = kontainerBlueprint.useWith(
    // default language
    Translations.withLocale("de"),
    // user record provider
    StaticUserRecordProvider(user),
    // the current session
    session
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

        logger.debug(call.kontainer.dump())
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
                        ),
                        call.sessions
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
