package de.peekandpoke

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.feature
import io.ktor.auth.*
import io.ktor.html.respondHtml
import io.ktor.http.HttpMethod
import io.ktor.locations.*
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.*
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.resources.css
import io.ultra.ktor_tools.resources.iocWebResources
import io.ultra.ktor_tools.semanticui.ui
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
fun Route.login(authName: String, users: UserHashedTableAuth) {


    /**
     * Installs the Authentication feature that handles the challenge and parsing and attaches a [UserIdPrincipal]
     * to the [ApplicationCall] if the authentication succeedes.
     */
    application.feature(Authentication).configure {

        form(authName) {

            userParamName = Login::userName.name
            passwordParamName = Login::password.name

            challenge = FormAuthChallenge.Redirect {
                // remember the url the was requested in a session
                sessions.set(LoginSession(request.uri))
                // redirect to the login page
                url(Login(it?.name ?: "")) { parameters.clear() }
            }

            validate {
                users.authenticate(it)
            }

            skipWhen {
                it.sessions.get<UserSession>()?.userId != null
            }
        }
    }

    get("/logout") {
        call.sessions.set(UserSession())
        call.respond("Logged out")
    }

    /**
     * For the [Login] route:
     */
    location<Login> {
        /**
         * We have an authenticated POST handler, that would set a session when the [UserIdPrincipal] is set,
         * and would redirect to the originally requested page.
         */
        authenticate(authName) {
            post {
                val principal = call.principal<UserIdPrincipal>()
                if (principal != null) {
                    call.sessions.set(UserSession(principal.name))
                }
                call.respondRedirect(
                    call.sessions.get<LoginSession>()?.requestedUri ?: "/"
                )
            }
        }

        /**
         * For a GET method, we respond with an HTML with a form asking for the user credentials.
         */
        method(HttpMethod.Get) {

            handle<Login> {

                if (call.sessions.get<UserSession>()?.userId != null) {
                    call.respondRedirect(
                        call.sessions.get<LoginSession>()?.requestedUri ?: "/"
                    )
                    return@handle
                }

                val webResources = call.iocWebResources

                call.respondHtml {

                    head {
                        title { +"Admin area" }

                        css(webResources["semantic"])
                    }

                    body {

                        ui.container {
                            style = "padding-top: 10%"

                            ui.grid.centered.middle.aligned {

                                ui.row {

                                    ui.sixteen.wide.tablet.six.wide.computer.column {

                                        ui.form Form {
                                            action = call.url(Login()) { parameters.clear() }
                                            method = FormMethod.post

                                            ui.field {
                                                label {
                                                    attributes["for"] = Login::userName.name
                                                    +"User"
                                                }
                                                textInput {
                                                    name = Login::userName.name
                                                }
                                            }

                                            ui.field {
                                                label {
                                                    attributes["for"] = Login::password.name
                                                    +"Password"
                                                }
                                                textInput {
                                                    name = Login::password.name
                                                }
                                            }

                                            ui.wide.button Submit { +"Login" }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
