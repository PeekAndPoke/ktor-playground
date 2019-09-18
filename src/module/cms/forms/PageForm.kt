package de.peekandpoke.module.cms.forms

import de.peekandpoke.module.cms.CmsPage
import de.peekandpoke.module.cms.CmsPageMutator
import io.ultra.ktor_tools.formidable.Form
import io.ultra.ktor_tools.formidable.MutatorForm
import io.ultra.ktor_tools.formidable.acceptsNonBlank
import io.ultra.ktor_tools.formidable.field

class PageForm(id: String, target: CmsPageMutator, parent: Form? = null) : MutatorForm<CmsPage>(target, "page[${id}]", parent) {

    val name = field(target::name).acceptsNonBlank()

    val slug = field(target::slug).acceptsNonBlank()

    val markup = field(target::markup).acceptsNonBlank()
}
