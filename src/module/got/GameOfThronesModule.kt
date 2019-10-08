package de.peekandpoke.module.got

import de.peekandpoke.karango.examples.game_of_thrones.*
import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.broker.getOrPost
import de.peekandpoke.ktorfx.flashsession.flashSession
import de.peekandpoke.ktorfx.flashsession.success
import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.module.got.views.characters
import de.peekandpoke.module.got.views.editCharacter
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.vault.Stored
import io.ktor.application.call
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ultra.ktor_tools.database

val GameOfThronesModule = module {
    // config
    config("gotMountPoint", "/game-of-thrones")

    // application
    singleton(GameOfThronesRoutes::class)
    singleton(GameOfThrones::class)

    // database
    singleton(CharactersRepository::class)
    singleton(ActorsRepository::class)
}

class GameOfThronesRoutes(converter: OutgoingConverter, gotMountPoint: String) : Routes(converter, gotMountPoint) {

    data class GetCharacters(val page: Int = 1, val epp: Int = 100)

    val getCharacters = route<GetCharacters>("/characters")
    fun getCharacters(page: Int = 1, epp: Int = 100) = getCharacters(GetCharacters(page, epp))

    data class GetCharacter(val character: Stored<Character>)

    val getCharacter = route<GetCharacter>("/characters/{character}")
    fun getCharacter(character: Stored<Character>) = getCharacter(GetCharacter(character))
}

class GameOfThrones(val routes: GameOfThronesRoutes) {

    fun Route.mount() {

        get(routes.getCharacters) { p ->
            val characters = database.characters.findAllPaged(p.page, p.epp)

            respond {
                characters(routes, characters)
            }
        }

        getOrPost(routes.getCharacter) { data ->

            val form = CharacterForm.of(data.character)

            println(data.character)

            if (form.submit(call)) {
                if (form.isModified) {

                    form.result.value.actor?.let { database.actors.save(it.asStored) }
                    val saved = database.characters.save(form.result)

                    flashSession.success("Character ${saved.value.fullName} was saved")
                }

                return@getOrPost call.respondRedirect(routes.getCharacters())
            }

            respond {
                editCharacter(routes, data.character, form)
            }
        }
    }
}


