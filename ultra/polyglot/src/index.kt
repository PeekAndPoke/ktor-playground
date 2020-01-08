package de.peekandpoke.ultra.polyglot

import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module

fun KontainerBuilder.ultraPolyglot() = module(Ultra_Polyglot)

val Ultra_Polyglot = module {

    // collector for all I18nGroup classes
    singleton(I18nTexts::class)
    // the I18n
    singleton(I18n::class)
    // the local selector - can be overwritten
    dynamic0 { I18nLocaleSelector("en", "en") }
}

fun String?.translatable(vararg replacements: Pair<String, String?>) = Translatable(this ?: "", replacements.toList())

fun String?.untranslated() = Translatable.untranslated(this ?: "")
