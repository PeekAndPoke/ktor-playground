package de.peekandpoke.module.got.view

import io.ktor.application.ApplicationCall
import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import io.ultra.ktor_tools.i18n
import kotlinx.html.*

class MainTemplate(call: ApplicationCall) : Template<HTML> {

    val t = call.i18n

    val pageTitle = Placeholder<HEAD>()
    val content = Placeholder<MAIN>()


    init {
        pageTitle {
            title { +"Game of Thrones" }
        }
    }

    override fun HTML.apply() {

        head {
            insert(pageTitle)
        }

        body {
            div {
                main {
                    insert(content)
                }
            }
        }
    }
}


