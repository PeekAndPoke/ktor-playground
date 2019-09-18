package de.peekandpoke.ultra.vault

interface OnSaveHook {
    operator fun <T> invoke(obj: T): T
}
