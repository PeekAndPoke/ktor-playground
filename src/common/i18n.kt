package de.peekandpoke.common


class I18n(locale: String, private val fallbackLocale: String, private val texts: Map<String, Texts>) {

    private val primary = texts[locale] ?: texts[fallbackLocale] ?: throw Exception("Locale not present $locale")

    private val fallback = texts[fallbackLocale] ?: throw Exception("Locale not present $locale")

    operator fun get(key: String, vararg replacements: Pair<String, String>): String {

        var res = primary.texts[key] ?: fallback.texts[key] ?: "!!!$key!!!"

        if (replacements.isEmpty()) {
            return res
        }

        replacements.forEach { res = res.replace(it.first, it.second) }

        return res
    }

    fun withLocale(loc: String) : I18n {

        if (texts.containsKey(loc)) {
            return I18n(loc, fallbackLocale, texts)
        }

        return this
    }
}

class Texts(val texts: Map<String, String>)
