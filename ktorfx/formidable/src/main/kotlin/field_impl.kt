package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ktorfx.formidable.rendering.FormidableViewBuilder
import de.peekandpoke.ultra.polyglot.Translatable
import io.ktor.http.Parameters
import kotlin.reflect.KMutableProperty0

/**
 * Converts a [FormField] to a [FormFieldWithOptions]
 */
fun <T> FormField<T>.withOptions(vararg options: Pair<T, Translatable>): FormFieldWithOptions<T> = withOptions(options.toList())

/**
 * Converts a [FormField] into a [FormFieldWithOptions]
 */
fun <T> FormField<T>.withOptions(options: List<Pair<T, Translatable>>): FormFieldWithOptions<T> =
    FormFieldWithOptionsImpl(this, options).also {
        resultingInAnyOf(options.map { (k, _) -> k }.toSet())
    }

/**
 * Converts a [FormField] into a [FormFieldWithOptions]
 */
fun <T> FormField<T>.withOptions(default: Translatable, options: List<Pair<T, Translatable>>): FormFieldWithOptions<T> =
    FormFieldWithOptionsImpl(this, options, default).also {
        resultingInAnyOf(options.map { (k, _) -> k }.toSet())
    }

/**
 * Converts a [FormField] into a [HiddenFormField]
 */
fun <T> FormField<T>.hidden(): HiddenFormField<T> = HiddenFormFieldImpl(this)

/**
 * Internal implementation of [FormField]
 */
internal class FormFieldImpl<T>(
    override val parent: Form,
    private val name: String,
    override val value: T,
    private val setter: (T) -> Unit,
    private val toStr: (T) -> String,
    private val fromStr: (String) -> T
) :
    FormField<T>, FormElement {

    constructor(parent: Form, prop: KMutableProperty0<T>, toStr: (T) -> String, fromStr: (String) -> T) : this(
        parent,
        prop.name,
        prop.getter(),
        prop.setter,
        toStr,
        fromStr
    )

    /**
     * Public getter for the text value of the field
     */
    override val textValue get() = _textValue.applyMappers()

    /**
     * Public getter for the errors of the field
     */
    override val errors get() = _errors

    /**
     * String to String mappers add via [addMapper]
     */
    private val mappers = mutableListOf<(String) -> String>()

    /**
     * Accept Rules added via [addAcceptRule]
     */
    private val acceptRules = mutableListOf<Pair<AcceptRule, Translatable>>()

    /**
     * Result Rules added via [addResultRule]
     */
    private val resultRules = mutableListOf<Pair<Rule<T>, Translatable>>()

    /**
     * The initial or updated text value of the field. Text mappers are applied on the initial value
     */
    private var _textValue = toStr(value)

    /**
     * Internal list of errors
     */
    private var _errors = listOf<Translatable>()

    /**
     * Get the unique id
     */
    override fun getId(): FormElementId = parent.getId() + name

    /**
     * Returns true when the fields is valid
     */
    override fun isValid() = _errors.isEmpty()

    /**
     * Adds a String to String mapper.
     */
    override fun addMapper(mapper: (String) -> String) = apply { mappers.add(mapper) }

    /**
     * Adds an Accept Rule.
     */
    override fun addAcceptRule(errorMessage: Translatable, rule: AcceptRule) = apply { acceptRules.add(rule to errorMessage) }

    /**
     * Add a Result Rule.
     */
    override fun addResultRule(errorMessage: Translatable, rule: Rule<T>) = apply { resultRules.add(rule to errorMessage) }

    /**
     * Submits the incoming [params] to the field
     */
    override fun submit(params: Parameters) {

        val id = getId().value

        if (params.contains(id)) {
            // get the input value
            val input = params[id] ?: ""

            // update the internal value of the form-field and apply all mappers (like trimmed)
            _textValue = input.applyMappers()

            // validate accept rules
            _errors = acceptRules.filter { !it.first(input) }.map { it.second }

            if (_errors.isEmpty()) {

                // Map the value back to the original data type
                val mapped: T = fromStr(_textValue)

                // validate result rules
                _errors = resultRules.filter { !it.first(mapped) }.map { it.second }

                // When all is fine, we can write the value back to the target of the form
                if (_errors.isEmpty()) {
                    setter(mapped)
                }
            }
        } else {
            _errors = listOf(FormidableI18n.must_not_be_empty)
        }
    }

    /**
     * Maps the given value to a string and applies the mappers.
     *
     * This is f.e. useful for Select fields, where multiple values
     * need to be transformed into the correct string representations
     *
     * @see [FormidableViewBuilder.selectInput]
     */
    override fun mapToView(value: T) = toStr(value).applyMappers()

    /**
     * Internal helper to apply the text mappers
     */
    private fun String.applyMappers() = mappers.fold(this) { acc, mapper ->
        mapper(acc)
    }
}

/**
 * Internal implementation of [HiddenFormField]
 */
internal open class HiddenFormFieldImpl<T>(
    private val wrapped: FormField<T>
) : HiddenFormField<T>, FormField<T> by wrapped {

    data class Holder(var data: String)
}

/**
 * Internal implementation of [SubmissionCheckField]
 */
internal class SubmissionCheckFieldImpl(wrapped: FormField<String>) : SubmissionCheckField, HiddenFormFieldImpl<String>(wrapped)

/**
 * Internal implementation of [FormFieldWithOptions]
 */
internal class FormFieldWithOptionsImpl<T>(
    private val wrapped: FormField<T>,
    override val options: List<Pair<T, Translatable>>,
    override val default: Translatable? = null
) : FormFieldWithOptions<T>, FormField<T> by wrapped
