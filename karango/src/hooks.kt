package de.peekandpoke.karango

interface OnSaveHook {
    operator fun <T> invoke(obj: T): T
}
