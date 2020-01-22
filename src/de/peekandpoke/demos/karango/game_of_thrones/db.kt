package de.peekandpoke.demos.karango.game_of_thrones

import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango._id
import de.peekandpoke.karango.aql.Direction
import de.peekandpoke.karango.aql.EQ
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.vault.EntityRepository
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.common.kType
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.hooks.WithTimestamps
import de.peekandpoke.ultra.vault.hooks.WithUserRecord

internal val Database.characters get() = getRepository<CharactersRepository>()

val Characters = EntityCollection<Character>("got_characters", kType())

@WithTimestamps
@WithUserRecord
class CharactersRepository(driver: KarangoDriver) : EntityRepository<Character>(driver, Characters) {

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

//    fun explainFindAllWithActor() = explain { findAllWithActorQuery }
}

internal val Database.actors get() = getRepository<ActorsRepository>()

val Actors = EntityCollection<Actor>("got_actors", kType())

class ActorsRepository(driver: KarangoDriver) : EntityRepository<Actor>(driver, Actors)
