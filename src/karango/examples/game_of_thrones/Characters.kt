package de.peekandpoke.karango.examples.game_of_thrones

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.Karango
import de.peekandpoke.karango.Ref
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.vault.Stored

@Karango
@Mutable
data class Character(
    val name: String,
    val surname: String? = null,
    val alive: Boolean,
    val age: Int? = null,
    val traits: List<String>,
    @Ref val actor: Actor? = null,
    val house: House? = null,
    override val _id: String? = null,
    override val _key: String? = null
) : Entity {

//    fun fullName() = listOfNotNull(name, surname).joinToString(" ")

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
