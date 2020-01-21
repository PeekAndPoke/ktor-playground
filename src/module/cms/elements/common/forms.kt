package de.peekandpoke.module.cms.elements.common

import de.peekandpoke.ktorfx.formidable.MutableListField
import de.peekandpoke.ktorfx.formidable.rendering.FormidableViewBuilder
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.domain.ImageMutator
import de.peekandpoke.module.cms.forms.ImageForm
import kotlinx.html.FlowContent
import kotlinx.html.img
import kotlinx.html.style

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
