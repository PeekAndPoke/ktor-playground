package de.peekandpoke.ultra.depot

import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module

fun KontainerBuilder.ultraDepot() = module(Ultra_Depot)

val Ultra_Depot = module {

    singleton(Depot::class)
}
