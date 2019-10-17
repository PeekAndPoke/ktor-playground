package de.peekandpoke.resources

import de.peekandpoke.ultra.polyglot.I18n
import de.peekandpoke.ultra.polyglot.I18nGroup
import de.peekandpoke.ultra.polyglot.translatable

val I18n.app get() = getGroup(AppI18n::class)

class AppI18n : I18nGroup, AppI18nTexts() {

    // make the texts statically available
    companion object : AppI18nTexts()

    override val texts = mapOf(
        "de" to mapOf(
            "app.WELCOME" to "Herzlich willkommen!",
            "app.WELCOME_NAME" to "Herzlich willkommen %name%!"
        ),
        "en" to mapOf(
            "app.WELCOME" to "Welcome!",
            "app.WELCOME_NAME" to "Welcome {name}!"
        )
    )
}

@Suppress("FunctionName", "PropertyName")
open class AppI18nTexts() {

    val WELCOME = "app.WELCOME".translatable()

    fun WELCOME_NAME(name: String) = "app.WELCOME_NAME".translatable("name" to name)
}
