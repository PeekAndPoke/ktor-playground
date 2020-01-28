package de.peekandpoke.modules.cms.domain

import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.acceptsNonBlank
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.trimmed
import de.peekandpoke.modules.cms.Cms
import de.peekandpoke.ultra.polyglot.untranslated

class LinkForm(cms: Cms, it: LinkMutator) : MutatorForm<Link, LinkMutator>(it) {

    companion object {
        fun of(cms: Cms, it: Link) = LinkForm(cms, it.mutator())
    }

    val title = field(target::title).trimmed().acceptsNonBlank()

    val url = field(target::url)
        .addMapper { Link.normalizeUri(it) }
        .acceptsNonBlank()
        .addAcceptRule("Not a valid Link".untranslated()) {
            it.startsWith("http://") ||
                    it.startsWith("https://") ||
                    it.startsWith("mailto:") ||
                    it.startsWith("#")
            cms.hasPageByUri(it)
        }
}
