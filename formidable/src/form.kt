package de.peekandpoke.formidable

import de.peekandpoke.mutator.Mutator
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.request.httpMethod
import io.ktor.request.receive

abstract class Form(name: String = "", parent: Form? = null) : FormElement {

    private val _name: FieldName = if (parent != null) parent._name + name else FieldName(name)

    private val _children: MutableList<FormElement> = mutableListOf()

    override fun submit(params: Parameters) {

        _children.forEach { it.submit(params) }
    }

    override fun isValid(): Boolean = _children.all { it.isValid() }

    fun <T> add(name: String, value: T, setter: (T) -> Unit, toStr: (T) -> String, fromStr: (String) -> T): FormField<T> = addField(
        FormFieldImpl(_name + name, value, setter, toStr, fromStr)
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
