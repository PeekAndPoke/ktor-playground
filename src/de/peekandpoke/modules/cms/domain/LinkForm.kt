package de.peekandpoke.modules.cms.domain

import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.acceptsNonBlank
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.trimmed

class LinkForm(it: LinkMutator) : MutatorForm<Link, LinkMutator>(it) {

    companion object {
        fun of(it: Link) = LinkForm(it.mutator())
    }

    val title = field(target::title).trimmed().acceptsNonBlank()

    val url = field(target::url).trimmed().acceptsNonBlank()
}
