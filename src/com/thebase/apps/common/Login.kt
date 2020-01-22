package com.thebase.apps.common

import com.thebase.LoginSession
import com.thebase.UserSession
import de.peekandpoke.ktorfx.semanticui.semanticUi
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.webResources
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.feature
import io.ktor.auth.*
import io.ktor.html.respondHtml
import io.ktor.http.HttpMethod
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.*
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.KtorExperimentalAPI
import kotlinx.html.*


private const val loginUri = "/login"
private const val userField = "user"
private const val passwordField = "password"

@KtorExperimentalAPI
fun Route.login(authName: String, users: UserHashedTableAuth) {

    /**
     * Installs the Authentication feature that handles the challenge and parsing and attaches a [UserIdPrincipal]
     * to the [ApplicationCall] if the authentication succeedes.
     */
    application.feature(Authentication).configure {

        form(authName) {

            userParamName = userField
            passwordParamName = passwordField

            challenge {
                // remember the url the was requested in a session
                call.sessions.set(LoginSession(call.request.uri))
                // redirect to the login page
                call.respondRedirect(loginUri)
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

    route(loginUri) {
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

            handle {

                if (call.sessions.get<UserSession>()?.userId != null) {
                    call.respondRedirect(
                        call.sessions.get<LoginSession>()?.requestedUri ?: "/"
                    )
                    return@handle
                }

                call.respondHtml {

                    head {
                        title { +"Admin area" }

                        css(webResources.semanticUi)
                    }

                    body {

                        ui.container {
                            style = "padding-top: 10%"

                            ui.grid.centered.middle.aligned {

                                ui.row {

                                    ui.sixteen.wide.tablet.six.wide.computer.column {

                                        ui.form Form {
                                            action = loginUri
                                            method = FormMethod.post

                                            ui.field {
                                                label { attributes["for"] = userField; +"User" }
                                                textInput { name = userField }
                                            }

                                            ui.field {
                                                label { attributes["for"] = passwordField; +"Password" }
                                                textInput { name = passwordField }
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
