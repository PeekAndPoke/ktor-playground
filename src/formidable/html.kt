package de.peekandpoke.formidable

import kotlinx.html.FlowContent

fun FlowContent.field(field: RenderableField) = field.render(this)
