package de.peekandpoke

import com.fasterxml.jackson.databind.SerializationFeature
import de.peekandpoke.jointhebase.admin.JtbAdmin
import de.peekandpoke.ktorfx.common.provide
import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.insights.gui.InsightsGui
import de.peekandpoke.ktorfx.insights.instrumentWithInsights
import de.peekandpoke.ktorfx.insights.registerInsightsRouteTracer
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.ktorfx.webjars.BetterWebjars
import de.peekandpoke.ktorfx.webresources.AppMeta
import de.peekandpoke.module.cms.CmsAdmin
import de.peekandpoke.module.cms.CmsPublic
import de.peekandpoke.module.demos.forms.FormDemos
import de.peekandpoke.module.depot.DepotAdmin
import de.peekandpoke.module.got.GameOfThrones
import de.peekandpoke.module.semanticui.SemanticUi
import de.peekandpoke.ultra.kontainer.KontainerBlueprint
import de.peekandpoke.ultra.polyglot.I18nLocaleSelector
import de.peekandpoke.ultra.security.user.StaticUserRecordProvider
import de.peekandpoke.ultra.security.user.UserRecord
import de.peekandpoke.ultra.vault.Database
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
import io.ultra.ktor_tools.logger.logger
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.pre
import java.util.*
import kotlin.collections.set

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

val AppMeta = object : AppMeta() {}


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

    val di = ApplicationDi(this)

    val initKontainer = di.systemKontainer()

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

            respond(HttpStatusCode.InternalServerError) {

                content {
                    div {
                        +"Internal Server Error"
                    }

                    pre {
                        +(cause.toString())
                    }

                    pre {
                        +cause.stackTrace.joinToString("\n")
                    }
                }

            }
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

    // TODO: have a switch for live / stage / dev

    // TODO: fix to logging problem
    intercept(ApplicationCallPipeline.Features) {

        val requestId = UUID.randomUUID()

        logger.attach("req.Id", requestId.toString()) {
            proceed()
        }
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
            installKontainer(di.wwwKontainerBlueprint)

            // instrument the pipeline with insights collectors
            instrumentWithInsights()

            // mount the insights gui when present
            initKontainer.use(InsightsGui::class) { mount() }

            // mount application modules
            initKontainer.use(CmsPublic::class) { mount() }

            get("/__test__") {
                call.respond("OK")
            }
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  Admin area
        /////

        host("admin.*".toRegex()) {

            // install the Kontainer into the pipeline
            installKontainer(di.adminKontainerBlueprint)

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

                // mount main modules
                initKontainer.use(JtbAdmin::class) { mount() }

                // mount helper modules
                initKontainer.use(CmsAdmin::class) { mount() }
                initKontainer.use(DepotAdmin::class) { mount() }

                initKontainer.use(SemanticUi::class) { mount() }
                initKontainer.use(FormDemos::class) { mount() }

                initKontainer.use(GameOfThrones::class) { mount() }
            }
        }
    }
}

data class LoginSession(val requestedUri: String)

data class UserSession(val userId: String? = null, val count: Int = 0)

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
