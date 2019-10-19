package de.peekandpoke.ultra.logging

import de.peekandpoke.ultra.kontainer.InjectionContext
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module

fun KontainerBuilder.ultraLogging() = module(Ultra_Logging)

val Ultra_Logging = module {

    singleton(UltraLogManager::class)

    singleton(ConsoleAppender::class)

    prototype(Log::class) { manager: UltraLogManager, context: InjectionContext ->
        manager.getLogger(context.requestingClass)
    }
}
