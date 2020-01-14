package de.peekandpoke.module.cms.forms

import de.peekandpoke._sortme_.karsten.camelCaseDivide
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.StorableForm
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.withOptions
import de.peekandpoke.module.cms.Cms
import de.peekandpoke.module.cms.CmsPage
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.vault.Storable

class CmsPageChangeLayoutForm(cms: Cms, it: Storable<CmsPage>) : MutatorForm<CmsPageChangeLayout, CmsPageChangeLayoutMutator>(
    CmsPageChangeLayout(it.value.layout::class.qualifiedName!!).mutator(), StorableForm.key(
        it
    ) + "-layout"
) {
    private val options = cms.layouts.map { (k, _) ->
        (k.qualifiedName ?: "n/a") to (k.simpleName ?: "").camelCaseDivide().untranslated()
    }

    val layout = field(target::layout).withOptions(options)
}

@Mutable
data class CmsPageChangeLayout(val layout: String)
