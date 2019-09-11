package de.peekandpoke.karango.examples.game_of_thrones

import de.peekandpoke.karango.Cursor
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.DbEntityCollection
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.aql.Direction
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.aql.type

fun Db.registerGotCollections() {
    register(CharactersCollection(this))
    register(ActorsCollection(this))
}


internal val Db.characters get() = getEntityCollection<CharactersCollection>()

val Characters = EntityCollection<Character>("got_characters", type())

class CharactersCollection(db: Db) : DbEntityCollection<Character>(db, Characters) {

    fun findAllPaged(page: Int, epp: Int, direction: Direction = Direction.ASC): Cursor<Character> {

        return db.query {
            FOR(Characters) { c ->
                SORT(c.name, direction)
                LIMIT((page - 1) * epp, epp)
                RETURN(c)
            }
        }
    }
}

internal val Db.actors get() = getEntityCollection<ActorsCollection>()

val Actors = EntityCollection<Actor>("got_actors", type())

class ActorsCollection(db: Db) : DbEntityCollection<Actor>(db, Actors)
