package io.ultra.polyglot

typealias TextsByLocale =  Map<String, Map<String, String>>

internal fun TextsByLocale.merge(other: TextsByLocale) : TextsByLocale {

    val localesCombined = keys + other.keys

    val result : MutableMap<String, Map<String, String>> = mutableMapOf()

    localesCombined.forEach {
        result[it] = (this[it] ?: mapOf()) + (other[it] ?: mapOf())
    }

    return result.toMap()
}

fun buildI18n(locale: String, fallbackLocale: String, vararg texts: TextsByLocale) : I18n {

    var result : TextsByLocale = mapOf()

    texts.forEach {
        result = result.merge(it)
    }

    return I18n(locale, fallbackLocale, result)
}

// TODO: introduce common Interface I18n and derive NullI18n and SimpleI18n from it
class NullI18n : I18n("en", "en", mapOf("en" to mapOf()))

open class I18n(locale: String, private val fallbackLocale: String, private val texts: TextsByLocale) {

    private val fallback = texts[fallbackLocale] ?: throw Exception("Fallback locale '$locale' is not present")

    private val primary = texts[locale] ?: fallback

    operator fun get(key: String, vararg replacements: Pair<String, String>): String {

        var res = primary[key] ?: fallback[key] ?: "!!!$key!!!"

        if (replacements.isEmpty()) {
            return res
        }

        replacements.forEach { res = res.replace("%${it.first}%", it.second) }

        return res
    }

    operator fun get(unit: Translatable) : String = get(unit.key, *unit.replacements)

    fun withLocale(loc: String) : I18n {

        if (texts.containsKey(loc)) {
            return I18n(loc, fallbackLocale, texts)
        }

        return this
    }
}

fun String.translatable(vararg replacements: Pair<String, String>) : Translatable = Translatable(this, replacements)

data class Translatable(val key: String, val replacements: Array<out Pair<String, String>>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Translatable

        if (key != other.key) return false
        if (!replacements.contentEquals(other.replacements)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + replacements.contentHashCode()
        return result
    }
}
