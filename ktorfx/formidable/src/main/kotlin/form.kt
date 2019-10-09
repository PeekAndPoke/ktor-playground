package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.mutator.Mutator
import de.peekandpoke.ultra.vault.Storable
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.request.httpMethod
import io.ktor.request.receive

/**
 * Base implementation for all forms
 */
abstract class Form(name: String = "", parent: Form? = null) : FormElement {

    /**
     * The form name is constructed from the given name and an eventual parents form name
     */
    private val _name: FieldName = if (parent != null) parent._name + name else FieldName(name)

    /**
     * List of all child elements
     */
    private val _children: MutableList<FormElement> = mutableListOf()

    /**
     * Submits the given [params] to all children
     */
    override fun submit(params: Parameters) {
        _children.forEach {
            it.submit(params)
        }
    }

    /**
     * Return true when the form is valid, meaning that all of the children are valid
     */
    override fun isValid(): Boolean = _children.all { it.isValid() }

    /**
     * Creates and adds a [FormField]
     *
     * @param name    The name of the field, which will be used to render the fields markup
     * @param value   The initial value of the field
     * @param setter  The setter used to modify the property of the underlying object
     * @param toStr   Mapper function to convert the field value to a string used in the markup
     * @param fromStr Mapper that converts a string to the type of the property of the underlying object
     */
    fun <T> add(name: String, value: T, setter: (T) -> Unit, toStr: (T) -> String, fromStr: (String) -> T): FormField<T> = addField(
        FormFieldImpl(_name + name, value, setter, toStr, fromStr)
    )

    /**
     * Adds a [Form] as a child
     */
    fun <T : Form> add(field: T): T = addField(field)

    /**
     * Adds the given [FormElement] as a child
     */
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
