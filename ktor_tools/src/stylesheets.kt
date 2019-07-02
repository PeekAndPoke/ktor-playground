package io.ultra.ktor_tools

import azadev.kotlin.css.Stylesheet
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext
import io.ultra.common.md5

val iocStylesheetsKey = AttributeKey<Map<String, CSS>>("stylesheets")

inline val ApplicationCall.iocStylesheets: Map<String, CSS> get() = attributes[iocStylesheetsKey]

inline val PipelineContext<Unit, ApplicationCall>.iocStylesheets: Map<String, CSS> get() = call.iocStylesheets

fun Attributes.put(value: Map<String, CSS>) = put(iocStylesheetsKey, value)

fun css(builder: Stylesheet.() -> Unit) = CSS(Stylesheet(builder))

data class CSS(val css: Stylesheet) {

    val content: String by lazy {
        css.render()
    }

    val md5: String by lazy {
        content.md5()
    }
}

