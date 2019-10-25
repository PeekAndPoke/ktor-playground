package de.peekandpoke.module.got

import de.peekandpoke.karango.examples.game_of_thrones.*
import de.peekandpoke.ktorfx.common.texts.PeopleI18n
import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.ultra.vault.New
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.value

class ActorForm(it: Storable<Actor>, mutator: ActorMutator) : StorableForm<Actor, ActorMutator>(it, mutator) {

    companion object {
        fun of(it: Actor) = of(New(it))

        fun of(it: Storable<Actor>) = ActorForm(it, it.value.mutator())

        fun of(it: ActorMutator) = ActorForm(New(it.getInput()), it)
    }

    val id = field(target::name)

    val surname = field(target::surname)

    val age = field(target::age).resultingInRange(0..200)
}


class CharacterForm(it: Storable<Character>, mutator: CharacterMutator) : StorableForm<Character, CharacterMutator>(it, mutator) {

    companion object {

        fun of(it: Character) = of(New(it))

        fun of(it: Storable<Character>) = CharacterForm(it, it.value.mutator())

        fun of(it: CharacterMutator) = CharacterForm(New(it.getInput()), it)
    }

    val id = field(target::name).trimmed().acceptsNonBlank()

    val surname = field(target::surname).trimmed()

    val age = field(target::age).resultingInRange(0..200)

    val alive = field(target::alive).withOptions(
        true to PeopleI18n.alive,
        false to PeopleI18n.dead
    )

    val actor = target.actor?.let {
        subForm(ActorForm.of(it.value))
    }
}
