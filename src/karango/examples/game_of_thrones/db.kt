package de.peekandpoke.karango.examples.game_of_thrones

import de.peekandpoke.karango.*
import de.peekandpoke.karango.aql.*

fun Db.Builder.registerGotCollections() {
    addEntityCollection { db -> CharactersCollection(db) }
    addEntityCollection { db -> ActorsCollection(db) }
}


internal val Db.characters get() = getEntityCollection<CharactersCollection>()

val Characters = EntityCollection<Character>("got_characters", type())

class CharactersCollection(db: Db) : DbEntityCollection<Character>(db, Characters) {

    private val findAllWithActorQuery = FOR(coll) { character ->
        FOR(Actors) { actor ->
            FILTER(character.actor EQ actor._id)
            RETURN(character)
        }
    }

    fun findAllPaged(page: Int, epp: Int, direction: Direction = Direction.ASC) = find {
        FOR(Characters) { c ->
            SORT(c.name, direction)
            LIMIT((page - 1) * epp, epp)
            RETURN(c)
        }
    }

    fun findFirstByNameAndSurname(name: String, surname: String) = findFirst {
        FOR(coll) { c ->
            FILTER(c.name EQ name)
            FILTER(c.surname EQ surname)
            LIMIT(1)
            RETURN(c)
        }
    }

    fun findAllWithActor() = find { findAllWithActorQuery }

    fun explainFindAllWithActor() = explain { findAllWithActorQuery }
}

internal val Db.actors get() = getEntityCollection<ActorsCollection>()

val Actors = EntityCollection<Actor>("got_actors", type())

class ActorsCollection(db: Db) : DbEntityCollection<Actor>(db, Actors)

class ActorsRepository(driver: KarangoDriver): EntityRepository<Actor>(driver, Actors)
