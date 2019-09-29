package de.peekandpoke.ultra.vault

import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.vault.hooks.AnonymousUserRecordProvider
import de.peekandpoke.ultra.vault.hooks.TimestampedOnSaveHook
import de.peekandpoke.ultra.vault.hooks.UserRecordOnSaveHook
import de.peekandpoke.ultra.vault.hooks.UserRecordProvider

/**
 * Vault kontainer module.
 *
 * Defines two dynamic services :
 *
 * - [EntityCache] which defaults to [DefaultEntityCache]
 *
 * - [UserRecordProvider] which defaults to [AnonymousUserRecordProvider]
 */
val Vault = module {
    singleton(Database::class)

    singleton(SharedRepoClassLookup::class)
    dynamic(EntityCache::class) { DefaultEntityCache() }

    singleton(TimestampedOnSaveHook::class)
    singleton(UserRecordOnSaveHook::class)
    dynamic(UserRecordProvider::class) { AnonymousUserRecordProvider() }
}
