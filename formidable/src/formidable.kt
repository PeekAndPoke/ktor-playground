package de.peekandpoke.formidable

import io.ktor.http.Parameters

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

    val errors: List<String>

    fun isValid() : Boolean

    fun mapToString(value: T): String

    fun accepting(errorMessage: String, rule: AcceptRule): FormField<T>

    fun resultingIn(errorMessage: String, rule: Rule<T>): FormField<T>
}

interface FormFieldWithOptions<T> : FormField<T> {
    val options: List<Pair<T, String>>
}
