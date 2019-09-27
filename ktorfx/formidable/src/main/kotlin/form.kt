package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.mutator.Mutator
import de.peekandpoke.ultra.vault.Storable
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.request.httpMethod
import io.ktor.request.receive

abstract class Form(

    name: String = "",
    parent: Form? = null

) : FormElement {

    private val _name: FieldName = if (parent != null) parent._name + name else FieldName(name)

    private val _children: MutableList<FormElement> = mutableListOf()

    override fun submit(params: Parameters) {
        _children.forEach { it.submit(params) }
    }

    override fun isValid(): Boolean = _children.all { it.isValid() }

    fun <T> add(name: String, value: T, setter: (T) -> Unit, toStr: (T) -> String, fromStr: (String) -> T): FormField<T> =
        addField(
            FormFieldImpl(_name + name, value, setter, toStr, fromStr)
        )

    fun <T : Form> add(field: T): T = addField(field)

    private fun <T : FormElement> addField(field: T): T = field.apply { _children.add(this) }
}

abstract class MutatorFormBase<T : Any, M : Mutator<T>>(

    val target: M,
    name: String = "",
    parent: Form? = null

) : Form(name, parent) {

    suspend fun submit(call: ApplicationCall): Boolean {

        if (call.request.httpMethod != HttpMethod.Post) {
            return false
        }

        submit(call.receive<Parameters>())

        return isValid()
    }

    val isModified: Boolean get() = target.isModified()
}

abstract class MutatorForm<T : Any, M : Mutator<T>>(

    target: M,
    name: String = "",
    parent: Form? = null

) : MutatorFormBase<T, M>(target, name, parent) {

    val result: T get() = target.getResult()
}

abstract class StorableForm<T : Any, M : Mutator<T>>(

    private val stored: Storable<T>,
    mutator: M,
    parent: Form? = null,
    name: String = "${stored.value::class.java.simpleName}[${stored._key}]"

) : MutatorFormBase<T, M>(mutator, name, parent) {

    val result: Storable<T> get() = stored.withValue(target.getResult())
}
