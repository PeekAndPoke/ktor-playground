package de.peekandpoke.formidable

import io.ktor.http.Parameters

fun <T> FormField<T>.withOptions(vararg options: Pair<T, String>) = withOptions(options.toList())

fun <T> FormField<T>.withOptions(options: List<Pair<T, String>>) = withOptions("Invalid value", options)

fun <T> FormField<T>.withOptions(message: String, vararg options: Pair<T, String>) = withOptions(message, options.toList())

fun <T> FormField<T>.withOptions(message: String, options: List<Pair<T, String>>): FormFieldWithOptions<T> =
    FormFieldWithOptionsImpl(this, options).also {
        resultingInAnyOf(options.map { (k, _) -> k }, message)
    }

internal class FormFieldImpl<T>(
    private val _name: FieldName,
    private var _value: T,
    private val setter: (T) -> Unit,
    private val toStr: (T) -> String,
    private val fromStr: (String) -> T
) :
    FormField<T>, FormElement {

    override val name get() = _name
    override val value get() = _value
    override val textValue get() = _textValue
    override val errors get() = _errors

    private var _textValue = toStr(_value)
    private var _errors = listOf<String>()
    private val acceptRules = mutableListOf<Pair<AcceptRule, String>>()
    private val resultRules = mutableListOf<Pair<Rule<T>, String>>()

    override fun accepting(errorMessage: String, rule: AcceptRule) = apply { acceptRules.add(rule to errorMessage) }

    override fun resultingIn(errorMessage: String, rule: Rule<T>) = apply { resultRules.add(rule to errorMessage) }

    override fun isValid() = _errors.isEmpty()

    override fun submit(params: Parameters) {

        if (params.contains(_name.value)) {

            val input = params[_name.value] ?: ""
            // update the internal value of the form-field
            _textValue = input
            // validate accept rules
            _errors = acceptRules.filter { !it.first(input) }.map { it.second }

            if (_errors.isEmpty()) {

                val mapped: T = fromStr(textValue)
                // validate result rules
                _errors = resultRules.filter { !it.first(mapped) }.map { it.second }

                if (_errors.isEmpty()) {
                    // propagate the value to the result
                    setter(fromStr(textValue))
                }
            }
        }
    }

    override fun mapToString(value: T) = toStr(value)
}


internal class FormFieldWithOptionsImpl<T>(
    private val wrapped: FormField<T>,
    override val options: List<Pair<T, String>>
) :
    FormFieldWithOptions<T> {

    override val name: FieldName get() = wrapped.name

    override val value: T get() = wrapped.value

    override val textValue: String get() = wrapped.textValue

    override val errors: List<String> get() = wrapped.errors

    override fun mapToString(value: T) = wrapped.mapToString(value)

    override fun isValid() = wrapped.isValid()

    override fun accepting(errorMessage: String, rule: AcceptRule): FormFieldWithOptions<T> = apply {
        wrapped.accepting(errorMessage, rule)
    }

    override fun resultingIn(errorMessage: String, rule: Rule<T>): FormFieldWithOptions<T> = apply {
        wrapped.resultingIn(errorMessage, rule)
    }
}
