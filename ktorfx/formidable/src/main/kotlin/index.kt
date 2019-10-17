package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module

fun KontainerBuilder.ktorFxFormidable() = module(KtorFX_Formidable)

val KtorFX_Formidable = module {
    singleton(FormidableI18n::class)
}
