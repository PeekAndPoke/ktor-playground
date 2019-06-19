package de.peekandpoke.module.got

import de.peekandpoke.formidable.Form
import de.peekandpoke.formidable.text
import de.peekandpoke.formidable.themes.BootstrapForm
import de.peekandpoke.karango.examples.game_of_thrones.ActorMutator
import de.peekandpoke.karango.examples.game_of_thrones.CharacterMutator


class ActorForm(target: ActorMutator, parent: Form? = null) : BootstrapForm("actor[${target._id}]", parent) {

    val name = text(target::name)
    val age = text(target::age)
}

class CharacterForm(target: CharacterMutator, parent: Form? = null) : BootstrapForm("character[${target._id}]", parent) {

    val name = text(target::name)
    val surname = text(target::surname)
    val age = text(target::age)

    val actor = target.actor?.let {
        add { _ -> ActorForm(it, this) }
    }
}
