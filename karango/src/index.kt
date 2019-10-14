package de.peekandpoke.karango

import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module

fun KontainerBuilder.karango() = module(KarangoModule)

/**
 * Karango kontainer module
 *
 * When using this you need to provide the following:
 *
 * ```
 *   instance(ArangoDatabase::class, myArangoDatabase)
 * ```
 */
val KarangoModule = module {
    singleton(KarangoDriver::class)
}
