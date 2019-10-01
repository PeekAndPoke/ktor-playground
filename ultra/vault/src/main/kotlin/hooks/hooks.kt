package de.peekandpoke.ultra.vault.hooks

import de.peekandpoke.ultra.vault.Repository
import de.peekandpoke.ultra.vault.Storable

interface OnSaveHook {
    fun <R> apply(repo: Repository<R>, storable: Storable<R>): Storable<R>
}
