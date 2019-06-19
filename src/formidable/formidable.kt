package de.peekandpoke.formidable

import de.peekandpoke.mutator.Mutator
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.request.httpMethod
import io.ktor.request.receive

interface FormElement {
    fun submit(params: Parameters)

    fun isValid(): Boolean

//    fun accepting(rule: AcceptRule, errorMessage: String): FormElement


}

typealias Rule<T> = (value: T) -> Boolean

typealias AcceptRule = Rule<String>

data class FieldName(val value: String) {

    val asFormId by lazy {
        value.replace("[^a-zA-Z0-9]".toRegex(), "-")
    }

    operator fun plus(str: String) = when {

        value.isEmpty() -> FieldName(str)

        else -> FieldName("$value.$str")
    }
}

abstract class Form(name: String = "", parent: Form? = null) : FormElement {

    private val _name: FieldName = if (parent != null) parent._name + name else FieldName(name)

    private val _children: MutableList<FormElement> = mutableListOf()

//    private val acceptRules = mutableListOf<Pair<AcceptRule, String>>()
//
//    override fun accepting(rule: AcceptRule, errorMessage: String) = apply { acceptRules.add(rule to errorMessage) }

    override fun submit(params: Parameters) {

        _children.forEach { it.submit(params) }
    }

    override fun isValid(): Boolean = _children.all { it.isValid() }

    fun <T> add(name: String, value: T, setter: (T) -> Unit, toStr: (T) -> String, fromStr: (String) -> T): FormField<T> = addField(
        FormField(_name + name, value, setter, toStr, fromStr)
    )

    fun <T : Form> add(field: T): T = addField(field)

    private fun <T : FormElement> addField(field: T): T = field.apply { _children.add(this) }
}

abstract class MutatorForm<T : Any>(private val target: Mutator<T>, name: String = "", parent: Form? = null) : Form(name, parent) {

    suspend fun submit(call: ApplicationCall) : Boolean {

        if (call.request.httpMethod == HttpMethod.Post) {
            submit(call.receive<Parameters>())

            return isValid()
        }

        return false
    }

    val isModified get() = target.isModified()

    val result get() = target.getResult()
}

open class FormField<T>(
    private val _name: FieldName,
    private var _value: T,
    private val setter: (T) -> Unit,
    private val toStr: (T) -> String,
    private val fromStr: (String) -> T
) :
    FormElement {

    val name get() = _name
    val value get() = _value
    val textValue get() = _textValue
    val errors get() = _errors

    private var _textValue = toStr(_value)
    private var _errors = listOf<String>()
    private val acceptRules = mutableListOf<Pair<AcceptRule, String>>()
    private val outcomeRules = mutableListOf<Pair<Rule<T>, String>>()

    fun accepting(rule: AcceptRule, errorMessage: String) = apply { acceptRules.add(rule to errorMessage) }

    fun resultingIn(rule: Rule<T>, errorMessage: String) = apply { outcomeRules.add(rule to errorMessage) }

    override fun isValid() = _errors.isEmpty()

    override fun submit(params: Parameters) {

        if (params.contains(_name.value)) {

            val input = params[_name.value] ?: ""
            // update the internal value of the form-field
            _textValue = input
            // validate
            _errors = acceptRules.filter { !it.first(input) }.map { it.second }

            if (_errors.isEmpty()) {

                val mapped : T = fromStr(textValue)

                _errors = outcomeRules.filter { !it.first(mapped) }.map { it.second }

                if (_errors.isEmpty()) {
                    // propagate the value to the result
                    setter(fromStr(textValue))
                }
            }
        }
    }

    fun mapToString(value: T) = toStr(value)

    fun withOptions(vararg options: Pair<T, String>) = withOptions(options.toList())

    fun withOptions(options: List<Pair<T, String>>) = FormFieldWithOptions(_name, _value, setter, toStr, fromStr, options)
}

open class FormFieldWithOptions<T>(
    name: FieldName,
    value: T,
    setter: (T) -> Unit,
    toStr: (T) -> String,
    fromStr: (String) -> T,
    val options: List<Pair<T, String>>
) : FormField<T>(name, value, setter, toStr, fromStr) {

    // TODO: apply outcome rule that checks for the possible values

}
