package de.peekandpoke.modules.got

import de.peekandpoke.ultra.polyglot.I18n
import de.peekandpoke.ultra.polyglot.I18nGroup
import de.peekandpoke.ultra.polyglot.translatable

val I18n.got get() = getGroup(GotI18n::class)

class GotI18n : I18nGroup, GotI18nTexts() {

    // make the texts statically available
    companion object : GotI18nTexts()

    override val texts = mapOf(
        "de" to mapOf(
            "got.actor" to "Darsteller",
            "got.edit_actor" to "Darsteller {name} bearbeiten",
            "got.edit_character" to "Figur {name} bearbeiten",
            "got.title.characters" to "GoT Figuren"
        ),
        "en" to mapOf(
            "got.actor" to "Actor",
            "got.edit_actor" to "Edit actor {name}",
            "got.edit_character" to "Edit character {name}",
            "got.title.characters" to "GoT Characters"
        )
    )
}

@Suppress("FunctionName")
open class GotI18nTexts {

    val actor = "got.actor".translatable()

    fun edit_actor(name: String?) = "got.edit_actor".translatable("name" to name)

    fun edit_character(name: String) = "got.edit_character".translatable("name" to name)

    fun title_characters() = "got.title.characters".translatable()
}
