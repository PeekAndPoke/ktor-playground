package de.peekandpoke.module.got

import de.peekandpoke.karango.examples.game_of_thrones.Actor
import de.peekandpoke.karango.examples.game_of_thrones.ActorMutator
import de.peekandpoke.karango.examples.game_of_thrones.Character
import de.peekandpoke.karango.examples.game_of_thrones.CharacterMutator
import io.ultra.ktor_tools.formidable.*


class ActorForm(target: ActorMutator, parent: Form? = null) : MutatorForm<Actor>(target, "actor[${target._id}]", parent) {

    val name = field(target::name)
    val age = field(target::age).resultingInRange(0 .. 200)
}

class CharacterForm(target: CharacterMutator, parent: Form? = null) : MutatorForm<Character>(target, "character[${target._id}]", parent) {

    val name = field(target::name)
    val surname = field(target::surname)
    val age = field(target::age).resultingInRange(0 .. 200)

    val alive = field(target::alive).withOptions(true to "alive", false to "dead")

    val actor = target.actor?.let { add(ActorForm(it, this)) }
}
