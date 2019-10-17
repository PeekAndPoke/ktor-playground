package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ktorfx.formidable.semanticui.FormidableViewBuilder
import de.peekandpoke.ultra.polyglot.Translatable
import io.ktor.http.Parameters

/**
 * Defines any kind of validation rule
 */
typealias Rule<T> = (value: T) -> Boolean

/**
 * AcceptRules work on the incoming strings
 */
typealias AcceptRule = Rule<String>

/**
 * Represents the name of a form element
 */
data class FieldName(val value: String) {

    val asFormId by lazy {
        value.replace("[^a-zA-Z0-9]".toRegex(), "-")
    }

    operator fun plus(str: String) = when {

        value.isEmpty() -> FieldName(str)

        else -> FieldName("$value.$str")
    }
}

/**
 * Defines the most basic structure of any form element
 */
interface FormElement {
    /**
     * Submits the given [params] to the form element
     */
    fun submit(params: Parameters)

    /**
     * Returns true when the form element is valid
     */
    fun isValid(): Boolean
}

/**
 * Defines a form field
 */
interface FormField<T> : FormElement {

    /**
     * The name of the form field
     */
    val name: FieldName

    /**
     * The real value of the field
     */
    val value: T

    /**
     * The text value of the field
     */
    val textValue: String

    /**
     * List of all validation errors
     */
    val errors: List<Translatable>

    /**
     * Returns true when the fields has no errors
     */
    override fun isValid(): Boolean = errors.isEmpty()

    /**
     * Returns true when the field has errors
     */
    fun hasErrors(): Boolean = !isValid()

    /**
     * Maps the given value to a string and applies the mappers.
     *
     * This is f.e. useful for Select fields, where multiple values
     * need to be transformed into the correct string representations
     *
     * @see [FormidableViewBuilder.selectInput]
     */
    fun mapToView(value: T): String

    /**
     * Adds a String to String mapper.
     *
     * The Mappers are applied to the content that is shown and the value that are received
     */
    fun addMapper(mapper: (String) -> String): FormField<T>

    /**
     * Adds an Accept Rule.
     *
     * These Rules that are applied on the received text
     *  -> after the mappers where applied.
     */
    fun addAcceptRule(errorMessage: Translatable, rule: AcceptRule): FormField<T>

    /**
     * Add a Result Rule.
     *
     * These Rules are applied on the real value
     *  -> after the mappers where applied
     *  -> and the string was converted to the real value
     */
    fun addResultRule(errorMessage: Translatable, rule: Rule<T>): FormField<T>
}

/**
 * Special type of field, for hidden form fields
 */
interface HiddenFormField<T> : FormField<T>

/**
 * Special type of field, for csrf form fields
 */
interface CsrfFormField : HiddenFormField<String>

/**
 * Special type of field, which can by used to f.e. render a [FormidableViewBuilder.selectInput]
 */
interface FormFieldWithOptions<T> : FormField<T> {
    val options: List<Pair<T, Translatable>>
}
