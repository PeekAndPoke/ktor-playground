package de.peekandpoke.module.got

import io.ktor.application.ApplicationCall
import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import io.ultra.ktor_tools.iocStylesheets
import io.ultra.ktor_tools.iocTranslations
import kotlinx.html.*

class MainTemplate(call: ApplicationCall) : Template<HTML> {

    val t = call.iocTranslations
    private val stylesheets = call.iocStylesheets

    val pageTitle = Placeholder<HEAD>()
    val content = Placeholder<BODY>()

    init {
        pageTitle {
            title { +"Game of Thrones" }
        }
    }

    override fun HTML.apply() {

        head {
            link(rel = "stylesheet", href = "/assets/bootstrap/css/bootstrap.css")
            insert(pageTitle)

            stylesheets.forEach {
                link(href = "/styles/${it.key}?v=${it.value.md5}", rel = "stylesheet")
            }
        }

        body {
            insert(content)
        }
    }
}


