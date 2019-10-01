package de.peekandpoke.ultra.insights

import de.peekandpoke.ultra.kontainer.module

val InsightsModule = module {

    singleton(Insights::class)
    singleton(InsightsMapper::class)
    instance(InsightsRepository::class, InsightsFileRepository())
}

