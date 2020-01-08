package de.peekandpoke.module.cms.forms

import de.peekandpoke._sortme_.camelCaseDivide
import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.module.cms.Cms
import de.peekandpoke.module.cms.CmsPage
import de.peekandpoke.module.cms.CmsPageMutator
import de.peekandpoke.module.cms.mutator
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
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
}

@Mutable
data class CmsPageChangeLayout(val layout: String)

class CmsPageChangeLayoutForm(cms: Cms, it: Storable<CmsPage>) : MutatorForm<CmsPageChangeLayout, CmsPageChangeLayoutMutator>(
    CmsPageChangeLayout(it.value.layout::class.qualifiedName!!).mutator(), StorableForm.key(it) + "-layout"
) {
    private val options = cms.layouts.map { (k, _) ->
        (k.qualifiedName ?: "n/a") to (k.simpleName ?: "").camelCaseDivide().untranslated()
    }

    val layout = field(target::layout).withOptions(options)
}
