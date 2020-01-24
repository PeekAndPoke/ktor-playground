package de.peekandpoke.modules.cms.domain

import com.thebase._sortme_.karsten.camelCaseDivide
import de.peekandpoke.ktorfx.formidable.Form
import de.peekandpoke.ktorfx.formidable.StorableForm
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.withOptions
import de.peekandpoke.modules.cms.Cms
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.vault.Storable

class CmsSnippetChangeElementForm(cms: Cms, it: Storable<CmsSnippet>) : Form(StorableForm.key(it) + "-layout") {

    data class Data(var element: String)

    val data = Data(it.value.element::class.qualifiedName ?: "")

    private val options = cms.elements.map { (k, _) ->
        (k.qualifiedName ?: "n/a") to (k.simpleName ?: "").camelCaseDivide().untranslated()
    }

    val element = field(data::element).withOptions("---".untranslated(), options)
}
