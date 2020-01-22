package com.thebase.apps.cms.elements.common

import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.ktorfx.formidable.rendering.FormidableViewBuilder
import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.modules.cms.domain.ImageForm
import de.peekandpoke.modules.cms.domain.ImageMutator
import de.peekandpoke.ultra.polyglot.untranslated
import kotlinx.html.FlowContent
import kotlinx.html.img
import kotlinx.html.style
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

fun FormidableViewBuilder.partial(flow: FlowContent, form: ElementStyle.Form) = flow.apply {

    ui.two.fields {
        selectInput(form.textColor, "Text color")
        selectInput(form.backgroundColor, "Background color")
    }
}

fun FormidableViewBuilder.partial(flow: FlowContent, images: MutableListField<ImageMutator, ImageForm>) = flow.apply {

    listFieldAsGrid(images) { item ->

        textInput(item.url, "Url")
        textInput(item.alt, "Alt Text")

        img(src = item.url.textValue, alt = item.alt.textValue) {
            style = "max-width: 100%; max-height: 200px;"
        }
    }
}
