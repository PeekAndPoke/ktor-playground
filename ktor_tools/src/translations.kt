package io.ultra.ktor_tools

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext
import io.ultra.polyglot.I18n

val iocTranslationsKey = AttributeKey<I18n>("i18n")

inline val ApplicationCall.iocTranslations: I18n get() = attributes[iocTranslationsKey]

inline val PipelineContext<Unit, ApplicationCall>.iocTranslations: I18n get() = call.iocTranslations

fun Attributes.put(value: I18n) = put(iocTranslationsKey, value)
