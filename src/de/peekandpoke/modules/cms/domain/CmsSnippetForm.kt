package de.peekandpoke.modules.cms.domain

import de.peekandpoke.ktorfx.formidable.StorableForm
import de.peekandpoke.ktorfx.formidable.acceptsNonBlank
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.trimmed
import de.peekandpoke.ultra.vault.New
import de.peekandpoke.ultra.vault.Storable

class CmsSnippetForm(it: Storable<CmsSnippet>, mutator: CmsSnippetMutator) : StorableForm<CmsSnippet, CmsSnippetMutator>(it, mutator) {

    companion object {
        fun of(it: CmsSnippet) = of(New(it))

        fun of(it: Storable<CmsSnippet>) = CmsSnippetForm(it, it.value.mutator())

        fun of(it: CmsSnippetMutator) = CmsSnippetForm(New(it.getInput()), it)
    }

    val name = field(target::name).trimmed().acceptsNonBlank()
}


