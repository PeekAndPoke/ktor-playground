package de.peekandpoke.modules.cms.domain

import com.thebase._sortme_.karsten.camelCaseDivide
import de.peekandpoke.ktorfx.formidable.Form
import de.peekandpoke.ktorfx.formidable.StorableForm
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.withOptions
import de.peekandpoke.modules.cms.Cms
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.vault.Storable

class CmsPageChangeLayoutForm(cms: Cms, it: Storable<CmsPage>) : Form(StorableForm.key(it) + "-layout") {

    data class Data(var layout: String)

    val data = Data(it.value.layout::class.qualifiedName ?: "")

    private val options = cms.layouts.map { (k, _) ->
        (k.qualifiedName ?: "n/a") to (k.simpleName ?: "").camelCaseDivide().untranslated()
    }

    val layout = field(data::layout).withOptions(options)
}
