package de.peekandpoke.module.got

import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI

data class GameOfThronesConfig(
    val mountPoint: String
) {

    companion object {
        @KtorExperimentalAPI fun from(config: ApplicationConfig) = GameOfThronesConfig(
            mountPoint = config.property("gameOfThrones.mountPoint").getString()
        )
    }
}
