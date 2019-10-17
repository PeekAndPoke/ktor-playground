package de.peekandpoke.ultra.polyglot

import kotlin.reflect.KClass

class I18n(private val texts: I18nTexts, private val selector: I18nLocaleSelector) {

    private val fallback by lazy { texts.forLocale(selector.fallback) ?: emptyMap() }

    private val primary by lazy { texts.forLocale(selector.locale) ?: fallback }

    fun <T : I18nGroup> getGroup(cls: KClass<T>): T = texts.getGroup(cls)
        ?: error("Translation group '$cls' is not present")

    inline operator fun invoke(callback: I18n.() -> Translatable) = get(this.callback())

    operator fun get(key: String, replacements: List<Pair<String, String?>>): String {

        var res = primary[key] ?: fallback[key] ?: "!!!$key!!!"

        if (replacements.isEmpty()) {
            return res
        }

        replacements.forEach { res = res.replace("{${it.first}}", it.second ?: "") }

        return res
    }

    operator fun get(unit: Translatable): String = get(unit.key, unit.replacements)

    fun withLocale(locale: String): I18n = I18n(texts, selector.copy(locale = locale))
}


