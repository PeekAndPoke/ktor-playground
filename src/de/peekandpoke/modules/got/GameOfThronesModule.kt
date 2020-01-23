package de.peekandpoke.modules.got

import de.peekandpoke.demos.karango.game_of_thrones.*
import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.broker.getOrPost
import de.peekandpoke.ktorfx.flashsession.flashSession
import de.peekandpoke.ktorfx.flashsession.success
import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.modules.got.views.characters
import de.peekandpoke.modules.got.views.editCharacter
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.vault.Stored
import io.ktor.application.call
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.database

@KtorExperimentalAPI
fun KontainerBuilder.gameOfThrones(config: GameOfThronesConfig) = module(GameOfThronesModule, config)

@KtorExperimentalAPI
val GameOfThronesModule = module { config: GameOfThronesConfig ->

    instance(config)
    singleton(GameOfThronesRoutes::class)
    singleton(GameOfThrones::class)
    singleton(GotI18n::class)

    // database
    singleton(CharactersRepository::class)
    singleton(ActorsRepository::class)
}

data class GameOfThronesConfig(
    val mountPoint: String
)

class GameOfThronesRoutes(converter: OutgoingConverter, config: GameOfThronesConfig) : Routes(converter, config.mountPoint) {

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
                characters(routes, characters.toList())
            }
        }

        getOrPost(routes.getCharacter) { data ->

            val form = CharacterForm.of(data.character)

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


