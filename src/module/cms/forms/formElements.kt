package de.peekandpoke.module.cms.forms

import de.peekandpoke.ktorfx.formidable.Form
import de.peekandpoke.ktorfx.formidable.FormFieldWithOptions
import de.peekandpoke.ktorfx.formidable.enum
import de.peekandpoke.ktorfx.formidable.withOptions
import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ultra.polyglot.untranslated
import kotlin.reflect.KMutableProperty0

fun Form.theBaseColors(prop: KMutableProperty0<SemanticColor>): FormFieldWithOptions<SemanticColor> = enum(prop).withOptions(
    SemanticColor.default to "default".untranslated(),
    SemanticColor.white to "white".untranslated(),
    SemanticColor.red to "light red".untranslated(),
    SemanticColor.green to "light green".untranslated(),
    SemanticColor.teal to "light blue".untranslated(),
    SemanticColor.violet to "dark violet".untranslated(),
    SemanticColor.olive to "dark green".untranslated(),
    SemanticColor.blue to "dark blue".untranslated(),
    SemanticColor.black to "black".untranslated()
)
