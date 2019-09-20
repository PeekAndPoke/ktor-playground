package de.peekandpoke.module.cms.forms

import de.peekandpoke.module.cms.CmsPage
import de.peekandpoke.module.cms.CmsPageMutator
import de.peekandpoke.module.cms.mutator
import de.peekandpoke.ultra.vault.New
import de.peekandpoke.ultra.vault.Storable
import io.ultra.ktor_tools.formidable.Form
import io.ultra.ktor_tools.formidable.StorableForm
import io.ultra.ktor_tools.formidable.acceptsNonBlank
import io.ultra.ktor_tools.formidable.field

class CmsPageForm private constructor(it: Storable<CmsPage>, mutator: CmsPageMutator, parent: Form?) :
    StorableForm<CmsPage, CmsPageMutator>(it, mutator, parent) {

    companion object {
        fun of(it: CmsPage, parent: Form? = null) = of(New(it), parent)

        fun of(it: Storable<CmsPage>, parent: Form? = null) = CmsPageForm(it, it.value.mutator(), parent)

        fun of(it: CmsPageMutator, parent: Form? = null) = CmsPageForm(New(it.getInput()), it, parent)
    }

    val name = field(target::name).acceptsNonBlank()

    val slug = field(target::slug).acceptsNonBlank()

    val markup = field(target::markup).acceptsNonBlank()
}
