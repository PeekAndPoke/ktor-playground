package de.peekandpoke.ultra.vault

import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.security.user.AnonymousUserRecordProvider
import de.peekandpoke.ultra.security.user.UserRecordProvider
import de.peekandpoke.ultra.vault.hooks.TimestampedOnSaveHook
import de.peekandpoke.ultra.vault.hooks.UserRecordOnSaveHook
import de.peekandpoke.ultra.vault.profiling.DefaultQueryProfiler
import de.peekandpoke.ultra.vault.profiling.QueryProfiler

fun KontainerBuilder.ultraVault() = module(Ultra_Vault)

/**
 * Vault kontainer module.
 *
 * Defines two dynamic services :
 *
 * - [EntityCache] which defaults to [DefaultEntityCache]
 *
 * - [UserRecordProvider] which defaults to [AnonymousUserRecordProvider]
 */
val Ultra_Vault = module {
    // Database
    singleton(Database::class)
    singleton(SharedRepoClassLookup::class)

    // Caching
    dynamic(EntityCache::class, DefaultEntityCache::class)

    // Profiling
    dynamic(QueryProfiler::class, DefaultQueryProfiler::class)

    // OnSaveHook for Timestamps
    singleton(TimestampedOnSaveHook::class)
    // OnSaveHook for UserRecords
    singleton(UserRecordOnSaveHook::class)

    // TODO: where do we put this one? E.g. formidable needs it as well
    dynamic(UserRecordProvider::class, AnonymousUserRecordProvider::class)
}
