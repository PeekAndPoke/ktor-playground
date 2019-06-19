package de.peekandpoke.formidable

import kotlinx.html.FlowContent

fun FlowContent.render(field: RenderableField, label: String) {
    label(field, label)
    field(field)
    errors(field)
}

fun FlowContent.errors(field: RenderableField) = field.renderErrors(this)

fun FlowContent.label(field: RenderableField, title: String) = field.renderLabel(this, title)

fun FlowContent.field(field: RenderableField) = field.renderField(this)
