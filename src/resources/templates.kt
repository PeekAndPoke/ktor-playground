package de.peekandpoke.resources

import io.ktor.application.ApplicationCall
import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import io.ultra.ktor_tools.resources.iocTranslations
import io.ultra.ktor_tools.resources.iocWebResources
import kotlinx.html.*

class MainTemplate(call: ApplicationCall) : Template<HTML> {

    val t = call.iocTranslations
    private val webResources = call.iocWebResources

    val pageTitle = Placeholder<HEAD>()
    val content = Placeholder<MAIN>()

    init {
        pageTitle {
            title { +"Game of Thrones" }
        }
    }

    override fun HTML.apply() {

        // see https://bootsnipp.com/snippets/Q0dAX "Pro Sidebar Template with Bootstrap 4"


        head {
            insert(pageTitle)

            webResources.css.forEach { css ->
                link(rel = "stylesheet", href = css.fullUri) {
                    css.integrity?.let { integrity = it }
                }
            }
        }

        body {

            div(classes = "page-wrapper chiller-theme toggled") {

                nav(classes = "sidebar-wrapper") {
                    id = "sidebar"

                    div(classes = "sidebar-content") {

                        div(classes = "sidebar-brand") {
                            a(href = "#") { +"SUPER APP" }

                            div {
                                id = "close-sidebar"
                                i(classes = "fa fa-times")
                            }
                        }

                        div(classes = "sidebar-header") {
                            div(classes = "user-pic") {
                                img(
                                    classes = "img-responsive img-rounded",
                                    src = "https://raw.githubusercontent.com/azouaoui-med/pro-sidebar-template/gh-pages/src/img/user.jpg",
                                    alt = "User"
                                )
                            }
                            div(classes = "user-info") {
                                span(classes = "user-name") {
                                    +"John"
                                    +" "
                                    strong { +"Smith" }
                                }
                                span(classes = "user-role") {
                                    +"Administrator"
                                }
                                span(classes = "user-status") {
                                    i(classes = "fa fa-circle")
                                    span { +"Online" }
                                }
                            }
                        }

                        div(classes = "sidebar-search") {
                            div(classes = "input-group") {
                                input(type = InputType.text, classes = "form-control search-menu") {
                                    placeholder = "Search..."
                                }
                                div(classes = "input-group-append") {
                                    span(classes = "input-group-text") {
                                        i(classes = "fa fa-search") {}
                                    }
                                }
                            }
                        }

                        div(classes = "sidebar-menu") {
                            ul {
                                li(classes = "header-menu") {
                                    span { +"General" }
                                }

                                li(classes = "sidebar-dropdown") {
                                    a(href = "#") {
                                        i(classes = "fa fa-tachometer-alt")
                                        span { +"Dashboard" }
                                        span(classes = "badge badge-pill badge-warning") { +"NEW" }
                                    }
                                    div(classes = "sidebar-submenu") {
                                        ul {
                                            li {
                                                a(href = "#") {
                                                    +"Dashboard 1"
                                                    span(classes = "badge badge-pill badge-success") { +"Pro" }
                                                }
                                            }
                                            li {
                                                a(href = "#") {
                                                    +"Dashboard 2"
                                                }
                                            }
                                            li {
                                                a(href = "#") {
                                                    +"Dashboard 3"
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                main(classes = "page-content") {
                    insert(content)
                }

                webResources.js.forEach { js ->
                    script(type = ScriptType.textJavaScript, src = js.fullUri) {
                        js.integrity?.let { integrity = it }
                    }
                }
            }
        }
    }
}


