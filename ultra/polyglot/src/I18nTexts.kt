package de.peekandpoke.ultra.polyglot

import de.peekandpoke.ultra.common.Lookup
import kotlin.reflect.KClass

class I18nTexts(private val groups: Lookup<I18nGroup>) {

    // merge all I18nGroups together
    private val texts by lazy {
        groups.all().fold(emptyMap()) { acc: I18nTextsByLocale, group -> acc.merge(group.texts) }
    }

    fun <T : I18nGroup> getGroup(cls: KClass<T>): T? = groups.get(cls)

    fun forLocale(locale: String): Map<String, String>? = texts[locale]
}
