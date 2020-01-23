package com.thebase.config

import de.peekandpoke.ktorfx.common.config.AppConfig
import de.peekandpoke.ktorfx.common.config.ktor.KtorConfig
import de.peekandpoke.modules.got.GameOfThronesConfig

data class TheBaseConfig(
    val ktor: KtorConfig,
    val gameOfThrones: GameOfThronesConfig
) : AppConfig
