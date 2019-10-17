package de.peekandpoke.ktorfx.common.texts

import de.peekandpoke.ultra.polyglot.I18n
import de.peekandpoke.ultra.polyglot.I18nGroup
import de.peekandpoke.ultra.polyglot.translatable

val I18n.forms get() = getGroup(FormsI18n::class)

class FormsI18n : I18nGroup, FormsI18nTexts() {

    // make the texts statically available
    companion object : FormsI18nTexts()

    override val texts = mapOf(
        "de" to mapOf(
            "common.forms.submit" to "Absenden"
        ),
        "en" to mapOf(
            "common.forms.submit" to "Submit"
        )
    )
}

open class FormsI18nTexts {
    val submit = "common.forms.submit".translatable()
}
