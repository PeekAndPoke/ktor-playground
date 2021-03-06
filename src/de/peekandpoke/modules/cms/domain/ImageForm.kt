package de.peekandpoke.modules.cms.domain

import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.acceptsNonBlank
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.trimmed

class ImageForm(it: ImageMutator) : MutatorForm<Image, ImageMutator>(it) {

    companion object {
        fun of(it: Image) = ImageForm(it.mutator())
    }

    val url = field(target::url).trimmed().acceptsNonBlank()

    val alt = field(target::alt).trimmed().acceptsNonBlank()
}
