package de.peekandpoke.module.cms.forms

import de.peekandpoke.ktorfx.formidable.StorableForm
import de.peekandpoke.ktorfx.formidable.acceptsNonBlank
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.module.cms.CmsPage
import de.peekandpoke.module.cms.CmsPageMutator
import de.peekandpoke.module.cms.mutator
import de.peekandpoke.ultra.vault.New
import de.peekandpoke.ultra.vault.Storable

class CmsPageForm(it: Storable<CmsPage>, mutator: CmsPageMutator) : StorableForm<CmsPage, CmsPageMutator>(it, mutator) {

    companion object {
        fun of(it: CmsPage) = of(New(it))

        fun of(it: Storable<CmsPage>) = CmsPageForm(it, it.value.mutator())

        fun of(it: CmsPageMutator) = CmsPageForm(New(it.getInput()), it)
    }

    val id = field(target::name).acceptsNonBlank()

    val slug = field(target::slug).acceptsNonBlank()

    val markup = field(target::markup).acceptsNonBlank()
}
