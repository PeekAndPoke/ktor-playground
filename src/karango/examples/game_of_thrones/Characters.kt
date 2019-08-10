package de.peekandpoke.karango.examples.game_of_thrones

import de.peekandpoke.karango.Cursor
import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.WithKey
import de.peekandpoke.karango.aql.Direction
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.aql.sort
import de.peekandpoke.karango.aql.type
import de.peekandpoke.karango.meta.Karango
import de.peekandpoke.karango.meta.Ref
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class Character(
    val name: String,
    val surname: String? = null,
    val alive: Boolean,
    val age: Int? = null,
    val traits: List<String>,
    @Ref val actor: Actor? = null,
    override val _id: String? = null,
    override val _key: String? = null
) : Entity, WithKey {

//    fun fullName() = listOfNotNull(name, surname).joinToString(" ")

    val fullName by lazy { listOfNotNull(name, surname).joinToString(" ") }
}

val Characters = CharactersCollection()

class CharactersCollection : EntityCollection<Character>("got_characters", type()) {

    fun findAllPaged(page: Int, epp: Int, direction: Direction = Direction.ASC): Cursor<Character> {

        return db.query {
            FOR(Characters) { c ->
                SORT(c.name.sort(direction))
                LIMIT((page - 1) * epp, epp)
                RETURN(c)
            }
        }

    }
}
