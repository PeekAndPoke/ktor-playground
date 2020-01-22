package de.peekandpoke.modules.cms.domain

import de.peekandpoke.ktorfx.formidable.StorableForm
import de.peekandpoke.ktorfx.formidable.acceptsNonBlank
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.trimmed
import de.peekandpoke.ultra.vault.New
import de.peekandpoke.ultra.vault.Storable

class CmsPageForm(it: Storable<CmsPage>, mutator: CmsPageMutator) : StorableForm<CmsPage, CmsPageMutator>(it, mutator) {

    companion object {
        fun of(it: CmsPage) = of(New(it))

        fun of(it: Storable<CmsPage>) = CmsPageForm(it, it.value.mutator())

        fun of(it: CmsPageMutator) = CmsPageForm(New(it.getInput()), it)
    }

    val name = field(target::name).trimmed().acceptsNonBlank()

    val slug = field(target::slug).trimmed()
}


