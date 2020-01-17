package de.peekandpoke.module.cms.forms

import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.acceptsNonBlank
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.module.cms.domain.Image
import de.peekandpoke.module.cms.domain.ImageMutator
import de.peekandpoke.module.cms.domain.mutator

class ImageForm(it: ImageMutator) : MutatorForm<Image, ImageMutator>(it) {

    companion object {
        fun of(it: Image) = ImageForm(it.mutator())
    }

    val url = field(target::url).acceptsNonBlank()

    val alt = field(target::alt).acceptsNonBlank()
}
