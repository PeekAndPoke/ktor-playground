package de.peekandpoke.ultra.polyglot

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.InputStream

fun InputStream.readPolyglotJson(): I18nTextsByLocale {

    val content = String(this.readBytes())

    val mapper = ObjectMapper().registerKotlinModule()

    return mapper.readValue(content)
}

