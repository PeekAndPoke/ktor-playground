package de.peekandpoke.module.got

import de.peekandpoke.karango.examples.game_of_thrones.*
import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.ultra.vault.New
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.value

class ActorForm private constructor(it: Storable<Actor>, mutator: ActorMutator, parent: Form?) :
    StorableForm<Actor, ActorMutator>(it, mutator, parent) {

    companion object {
        fun of(it: Actor, parent: Form? = null) = of(New(it), parent)

        fun of(it: Storable<Actor>, parent: Form? = null) = ActorForm(it, it.value.mutator(), parent)

        fun of(it: ActorMutator, parent: Form? = null) = ActorForm(New(it.getInput()), it, parent)
    }

    val name = field(target::name)

    val surname = field(target::surname)

    val age = field(target::age).resultingInRange(0..200)
}


class CharacterForm private constructor(it: Storable<Character>, mutator: CharacterMutator, parent: Form?) :
    StorableForm<Character, CharacterMutator>(it, mutator, parent) {

    companion object {

        fun of(it: Character, parent: Form? = null) = of(New(it), parent)

        fun of(it: Storable<Character>, parent: Form? = null) = CharacterForm(it, it.value.mutator(), parent)

        fun of(it: CharacterMutator, parent: Form? = null) = CharacterForm(New(it.getInput()), it, parent)
    }

    val name = field(target::name).trimmed().acceptsNonBlank()

    val surname = field(target::surname).trimmed()

    val age = field(target::age).resultingInRange(0..200)

    val alive = field(target::alive).withOptions(true to "alive", false to "dead")

    val actor = target.actor?.let { add(ActorForm.of(it.value, this)) }
}
