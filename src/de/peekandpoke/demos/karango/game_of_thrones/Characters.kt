package de.peekandpoke.demos.karango.game_of_thrones

import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.vault.Ref
import de.peekandpoke.ultra.vault.Stored

@Karango
@Mutable
data class Character(
    val name: String,
    val surname: String? = null,
    val alive: Boolean,
    val age: Int? = null,
    val traits: List<String>,
    val actor: Ref<Actor>? = null,
    val house: House? = null
) {
    val fullName by lazy { listOfNotNull(name, surname).joinToString(" ") }
}

val Stored<Character>.name get() = value.name
val Stored<Character>.surname get() = value.surname
val Stored<Character>.alive get() = value.alive
val Stored<Character>.age get() = value.age
val Stored<Character>.traits get() = value.traits
val Stored<Character>.actor get() = value.actor
val Stored<Character>.house get() = value.house
val Stored<Character>.fullName get() = value.fullName

fun Stored<Character>.mutate(mutation: CharacterMutator.() -> Unit) = copy(value = value.mutate(mutation))
