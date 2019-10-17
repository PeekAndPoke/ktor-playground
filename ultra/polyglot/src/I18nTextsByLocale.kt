package de.peekandpoke.ultra.polyglot

typealias I18nTextsByLocale = Map<String, Map<String, String>>

internal fun I18nTextsByLocale.merge(other: I18nTextsByLocale): I18nTextsByLocale {

    val localesCombined = keys + other.keys

    val result: MutableMap<String, Map<String, String>> = mutableMapOf()

    localesCombined.forEach {
        result[it] = (this[it] ?: mapOf()) + (other[it] ?: mapOf())
    }

    return result.toMap()
}
