package de.peekandpoke.karango

import de.peekandpoke.karango.slumber.KarangoCodec
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.common.TypedAttributes
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.slumber.Config
import de.peekandpoke.ultra.slumber.builtin.BuiltInModule
import de.peekandpoke.ultra.slumber.builtin.DateTimeModule
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.EntityCache
import de.peekandpoke.ultra.vault.slumber.VaultSlumberModule

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

    dynamic(KarangoDriver::class)

    val slumberConfig = Config(
        listOf(
            VaultSlumberModule,
            DateTimeModule,
            BuiltInModule
        )
    )

    dynamic(KarangoCodec::class) { database: Database, cache: EntityCache ->
        KarangoCodec(
            slumberConfig,
            TypedAttributes.of {
                add(VaultSlumberModule.DatabaseKey, database)
                add(VaultSlumberModule.EntityCacheKey, cache)
            }
        )
    }
}
