package de.peekandpoke

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.auth.*
import io.ktor.html.respondHtml
import io.ktor.http.HttpMethod
import io.ktor.locations.*
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.method
import io.ktor.routing.post
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.KtorExperimentalAPI
import kotlinx.html.*

/**
 * Location for login a [userName] with a [password].
 */
@KtorExperimentalLocationsAPI
@Location("/login")
data class Login(val userName: String = "", val password: String = "")

/**
 * Register [Login] related routes and features.
 */
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.login(auth: Authentication, users: UserHashedTableAuth) {
    val myFormAuthentication = "myFormAuthentication"

    /**
     * Installs the Authentication feature that handles the challenge and parsing and attaches a [UserIdPrincipal]
     * to the [ApplicationCall] if the authentication succeedes.
     */
    auth.configure {

        form(myFormAuthentication) {
            userParamName = Login::userName.name
            passwordParamName = Login::password.name
            challenge = FormAuthChallenge.Redirect { url(Login(it?.name ?: "")) { parameters.clear() } }
            validate { users.authenticate(it) }
            skipWhen {


                val session = it.sessions.get<MySession>()
                println(session)

                val skip = session?.userId != null

                return@skipWhen skip
            }
        }
    }

    get("/logout") {
        call.sessions.set(MySession())
        call.respond("Logged out")
    }

    /**
     * For the [Login] route:
     */
    location<Login> {
        /**
         * We have an authenticated POST handler, that would set a session when the [UserIdPrincipal] is set,
         * and would redirect to the [Index] page.
         */
        authenticate(myFormAuthentication) {
            post {
                val principal = call.principal<UserIdPrincipal>()
                if (principal != null) {
                    call.sessions.set(MySession(principal.name))
                }
                call.respondRedirect("/private/show")
            }
        }

        /**
         * For a GET method, we respond with an HTML with a form asking for the user credentials.
         */
        method(HttpMethod.Get) {
            handle<Login> {

                if (call.sessions.get<MySession>()?.userId != null) {
                    call.respondRedirect("/")
                    return@handle
                }

                call.respondHtml {
                    body {
                        h2 { +"Login" }
                        form(
                            call.url(Login()) { parameters.clear() },
                            classes = "pure-form-stacked",
                            encType = FormEncType.applicationXWwwFormUrlEncoded,
                            method = FormMethod.post
                        ) {
                            acceptCharset = "utf-8"

                            label {
                                +"Username: "
                                textInput {
                                    name = Login::userName.name
                                    value = it.userName
                                }
                            }
                            label {
                                +"Password: "
                                passwordInput {
                                    name = Login::password.name
                                }
                            }
                            submitInput(classes = "pure-button pure-button-primary") {
                                value = "Login"
                            }
                        }
                    }
                }
            }
        }
    }
}
