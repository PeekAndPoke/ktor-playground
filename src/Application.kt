package de.peekandpoke

import com.fasterxml.jackson.databind.SerializationFeature
import de.peekandpoke.karango.karango
import de.peekandpoke.ktorfx.common.provide
import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.insights.gui.InsightsGui
import de.peekandpoke.ktorfx.insights.instrumentWithInsights
import de.peekandpoke.ktorfx.insights.registerInsightsRouteTracer
import de.peekandpoke.ktorfx.security.KtorFXSecurityConfig
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.ktorfx.webjars.BetterWebjars
import de.peekandpoke.ktorfx.webresources.AppMeta
import de.peekandpoke.module.cms.CmsAdmin
import de.peekandpoke.module.cms.CmsPublic
import de.peekandpoke.module.cms.cmsAdmin
import de.peekandpoke.module.cms.cmsPublic
import de.peekandpoke.module.depot.DepotAdmin
import de.peekandpoke.module.depot.depotAdmin
import de.peekandpoke.module.got.GameOfThrones
import de.peekandpoke.module.got.gameOfThrones
import de.peekandpoke.module.semanticui.SemanticUi
import de.peekandpoke.module.semanticui.semanticUi
import de.peekandpoke.resources.AppI18n
import de.peekandpoke.ultra.kontainer.KontainerBlueprint
import de.peekandpoke.ultra.kontainer.kontainer
import de.peekandpoke.ultra.polyglot.I18nLocaleSelector
import de.peekandpoke.ultra.security.user.StaticUserRecordProvider
import de.peekandpoke.ultra.security.user.UserRecord
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.ultraVault
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
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.host
import io.ktor.routing.routing
import io.ktor.sessions.*
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.getDigestFunction
import io.ktor.util.hex
import io.ultra.ktor_tools.KtorFXConfig
import io.ultra.ktor_tools.ktorFx
import io.ultra.ktor_tools.logger.logger
import kotlinx.html.body
import kotlinx.html.div
import java.net.InetAddress
import java.time.Duration
import java.util.*
import kotlin.collections.set

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

val AppMeta = object : AppMeta() {}

private val commonKontainerBlueprint by lazy {

    // TODO: get the config from application.environment.config

    val config = KtorFXConfig(
//        KtorFXSecurityConfig("super-secret", 300_000)
        KtorFXSecurityConfig("super-secret", 300_000)
    )

    kontainer {

        // Database //////////////////////////////////////////////////////////////////////////////////////////////////////////
        ultraVault()

        // Add karango and provide connection to arango database
        karango()
        instance(arangoDatabase)


        // KtorFX ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ktorFx(config)
        // create a app specific cache buster
        instance(AppMeta.cacheBuster())

        // application ///////////////////////////////////////////////////////////////////////////////////////////////////////

        // i18n
        singleton(AppI18n::class)

        // modules
        cmsAdmin()
        cmsPublic()

        depotAdmin()

        // application modules
        gameOfThrones()
        semanticUi()

    }
}

private fun systemKontainer() = commonKontainerBlueprint.useWith(
    // user record provider
    StaticUserRecordProvider(
        UserRecord("system", InetAddress.getLocalHost().canonicalHostName)
    )
)

val Application.wwwKontainerBlueprint by lazy {
    commonKontainerBlueprint.extend {
        // set default html template
        prototype(SimpleTemplate::class, WwwTemplate::class)
    }
}

val Application.adminKontainerBlueprint by lazy {
    commonKontainerBlueprint.extend {
        // set default html template
        prototype(SimpleTemplate::class, AdminTemplate::class)
    }
}

fun Route.installKontainer(blueprint: KontainerBlueprint) {

    intercept(ApplicationCallPipeline.Setup) {

        val userRecord = UserRecord(
            call.sessions.get<UserSession>()?.userId ?: "anonymous",
            call.request.origin.remoteHost
        )

        val kontainer = blueprint.useWith(
            // default language
            I18nLocaleSelector("en", "en"),
            // user record provider
            StaticUserRecordProvider(userRecord),
            // session
            call.sessions
        )

        call.attributes.provide(kontainer)
    }
}


@KtorExperimentalAPI
@kotlin.jvm.JvmOverloads
@Suppress("unused", "UNUSED_PARAMETER") // Referenced in application.conf
fun Application.module(testing: Boolean = false) {

    val initKontainer = systemKontainer()

    // Ensure all database repositories are set up properly
    initKontainer.use(Database::class) { ensureRepositories() }

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

//    // TODO: have a switch for live / stage / dev
//    instrumentWithInsights(
//        initKontainer.get(InsightsGui::class)
//    )
//
//    intercept(ApplicationCallPipeline.Features) {
//
//        val userRecord = UserRecord(
//            call.sessions.get<UserSession>()?.userId ?: "anonymous",
//            call.request.origin.remoteHost
//        )
//
//        call.attributes.provide(requestContainer(userRecord , call.sessions))
//    }

    intercept(ApplicationCallPipeline.Features) {

        val requestId = UUID.randomUUID()

        logger.attach("req.Id", requestId.toString()) {
            proceed()
        }

//        logger.debug(call.kontainer.dump())
    }


    routing {

        registerInsightsRouteTracer()

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

            // install the Kontainer into the pipeline
            installKontainer(wwwKontainerBlueprint)

            // instrument the pipeline with insights collectors
            instrumentWithInsights()

            // mount the insights gui when present
            initKontainer.use(InsightsGui::class) { mount() }

            // mount application modules
            initKontainer.use(CmsPublic::class) { mount() }
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  Admin area
        /////

        host("admin.*".toRegex()) {

            // install the Kontainer into the pipeline
            installKontainer(adminKontainerBlueprint)

            // instrument the pipeline with insights collectors
            instrumentWithInsights()

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
                respond {
                    content {
                        ui.header H3 { +"Admin area" }
                    }
                }
            }

            authenticate(authName) {
                // mount the insights gui when present
                initKontainer.use(InsightsGui::class) { mount() }

                // mount application modules
                initKontainer.use(SemanticUi::class) { mount() }
                initKontainer.use(CmsAdmin::class) { mount() }
                initKontainer.use(DepotAdmin::class) { mount() }
                initKontainer.use(GameOfThrones::class) { mount() }
            }
        }
    }
}

data class LoginSession(val requestedUri: String)

data class UserSession(val userId: String? = null, val count: Int = 0)

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
