package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.polyglot.TextsByLocale
import de.peekandpoke.ultra.polyglot.Translatable
import de.peekandpoke.ultra.polyglot.readPolyglotJson
import io.ktor.http.Parameters

class Formidable {
    companion object {

        fun loadI18n(): TextsByLocale {
            val stream = Formidable::class.java.classLoader.getResourceAsStream("ktorfx/formidable/i18n.json")!!

            return stream.readPolyglotJson()
        }
    }
}


typealias Rule<T> = (value: T) -> Boolean

typealias AcceptRule = Rule<String>

interface FormElement {
    fun submit(params: Parameters)

    fun isValid(): Boolean
}

data class FieldName(val value: String) {

    val asFormId by lazy {
        value.replace("[^a-zA-Z0-9]".toRegex(), "-")
    }

    operator fun plus(str: String) = when {

        value.isEmpty() -> FieldName(str)

        else -> FieldName("$value.$str")
    }
}

interface FormField<T> {

    val name: FieldName

    val value: T

    val textValue: String

    val errors: List<Translatable>

    fun isValid(): Boolean = errors.isEmpty()

    fun hasErrors(): Boolean = !isValid()

    fun mapToString(value: T): String

    fun accepting(errorMessage: Translatable, rule: AcceptRule): FormField<T>

    fun resultingIn(errorMessage: Translatable, rule: Rule<T>): FormField<T>
}

interface FormFieldWithOptions<T> : FormField<T> {
    val options: List<Pair<T, String>>
}
