package de.peekandpoke.formidable

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.request.httpMethod
import io.ktor.request.receive
import kotlinx.html.FlowContent

interface FormField {
    fun submit(params: Parameters)

    fun isValid(): Boolean

    fun accepting(rule: AcceptRule, errorMessage: String): FormField
}

interface RenderableField : FormField {
    fun renderField(flow: FlowContent)
    fun renderLabel(flow: FlowContent, title: String)
    fun renderErrors(flow: FlowContent)

    override fun accepting(rule: AcceptRule, errorMessage: String): RenderableField
}

typealias Rule<T> = (value: T) -> Boolean

typealias AcceptRule = Rule<String>

data class FieldName(val value: String) {

    val toFormId by lazy {
        value.replace("[^a-zA-Z0-9]".toRegex(), "-")
    }

    operator fun plus(str: String) = when {

        value.isEmpty() -> FieldName(str)

        else -> FieldName("$value.$str")
    }
}

abstract class Form(name: String = "", parent: Form? = null) : FormField {

    private val _name: FieldName = if (parent != null) parent._name + name else FieldName(name)

    private val _children: MutableList<FormField> = mutableListOf()

    private val acceptRules = mutableListOf<Pair<AcceptRule, String>>()

    override fun accepting(rule: AcceptRule, errorMessage: String) = apply { acceptRules.add(rule to errorMessage) }

    fun <T : FormField> add(creator: (FieldName) -> T): T = creator(_name).apply { _children.add(this) }

    override fun submit(params: Parameters) {

        _children.forEach { it.submit(params) }
    }

    override fun isValid(): Boolean = _children.all { it.isValid() }

    suspend fun submit(call: ApplicationCall) : Boolean {

        if (call.request.httpMethod == HttpMethod.Post) {
            submit(call.receive<Parameters>())

            return isValid()
        }

        return false
    }

    abstract fun <T> text(name: String, value: String, setter: (T) -> Unit, fromStr: (String) -> T): RenderableField
}

abstract class GenericField<T>(
    protected val _name: FieldName,
    protected var textValue: String,
    private val setter: (T) -> Unit,
    private val fromStr: (String) -> T
) :
    FormField, RenderableField {

    protected var errors = listOf<String>()

    private val acceptRules = mutableListOf<Pair<AcceptRule, String>>()

    override fun accepting(rule: AcceptRule, errorMessage: String) = apply { acceptRules.add(rule to errorMessage) }

    override fun isValid() = errors.isEmpty()

    override fun submit(params: Parameters) {

        if (params.contains(_name.value)) {

            val input = params[_name.value] ?: ""

            // update the internal value of the form-field
            textValue = input

            errors = acceptRules.filter { !it.first(input) }.map { it.second }

            if (errors.isEmpty()) {

                // propagate the value to the result
                setter(fromStr(textValue))
            }
        }
    }
}

