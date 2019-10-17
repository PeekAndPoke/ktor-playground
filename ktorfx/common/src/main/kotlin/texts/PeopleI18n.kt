package de.peekandpoke.ktorfx.common.texts

import de.peekandpoke.ultra.polyglot.I18n
import de.peekandpoke.ultra.polyglot.I18nGroup
import de.peekandpoke.ultra.polyglot.translatable

val I18n.people get() = getGroup(PeopleI18n::class)

class PeopleI18n : I18nGroup, PeopleI18nTexts() {

    // make the texts statically available
    companion object : PeopleI18nTexts()

    override val texts = mapOf(
        "de" to mapOf(
            "common.people.age" to "Alter",
            "common.people.alive" to "Lebendig",
            "common.people.dead" to "Tot",
            "common.people.name" to "Name",
            "common.people.surname" to "Nachname"
        ),
        "en" to mapOf(
            "common.people.age" to "Age",
            "common.people.alive" to "Alive",
            "common.people.dead" to "Dead",
            "common.people.name" to "Name",
            "common.people.surname" to "Surname"
        )
    )
}

open class PeopleI18nTexts {
    val age = "common.people.age".translatable()
    val alive = "common.people.alive".translatable()
    val dead = "common.people.dead".translatable()
    val name = "common.people.name".translatable()
    val surname = "common.people.name".translatable()
}
