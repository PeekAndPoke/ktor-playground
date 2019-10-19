package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.common.receiveOrGet
import de.peekandpoke.ultra.mutator.Mutator
import de.peekandpoke.ultra.security.csrf.CsrfProtection
import de.peekandpoke.ultra.vault.Storable
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.request.httpMethod
import kotlin.reflect.KMutableProperty0

/**
 * Base implementation for all forms
 */
abstract class Form(name: String = "", private val parent: Form? = null) : FormElement {

    /**
     * Markup compatible id of the form
     */
    val formId get(): String = _name.asFormId

    /**
     * All hidden fields of the form
     */
    val hiddenFields get(): List<HiddenFormField<*>> = _children.filterIsInstance<HiddenFormField<*>>()

    /**
     * The form name is constructed from the given name and an eventual parents form name
     */
    private val _name: FieldName = if (parent != null) parent._name + name else FieldName(name)

    /**
     * List of all child elements
     */
    private val _children: MutableList<FormElement> = mutableListOf()

    /**
     * Flag that tracks if the form was submitted
     */
    private var isSubmitted = false

    /**
     * Flag for enabling / disabling csrf security
     */
    protected var csrfRequired: Boolean = true

    init {
        if (parent == null) {
            hidden("__submitted__", "")
        }
    }

    suspend fun submit(call: ApplicationCall): Boolean {

        // attach a csrf field if there is none already
        if (parent == null && !hasCsrfField()) {
            with(call) {
                kontainer.use(CsrfProtection::class) {
                    csrf(this)
                }
            }
        }

        if (call.request.httpMethod != HttpMethod.Post) {
            return false
        }

        submit(call.receiveOrGet())

        return isValid()
    }

    /**
     * Submits the given [params] to all children
     */
    override fun submit(params: Parameters) {

        checkSecuritySetup()

        val submissionCheckHiddenFieldName = (_name + "__submitted__").value

        if (parent == null && !params.contains(submissionCheckHiddenFieldName)) {
            return
        }

        isSubmitted = true

        _children.forEach {
            it.submit(params)
        }
    }

    /**
     * Returns true when the form was submitted
     */
    fun isSubmitted() = isSubmitted

    /**
     * Return true when the form is valid, meaning that all of the children are valid
     */
    override fun isValid(): Boolean = isSubmitted && _children.all { it.isValid() }

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
     * Adds a hidden field
     */
    fun hidden(name: String, property: KMutableProperty0<String>): HiddenFormField<String> = addField(
        FormFieldImpl(_name + name, property.getter(), property.setter, { it }, { it }).hidden()
    )

    /**
     * Adds a hidden field with a static string value
     */
    fun hidden(name: String, value: String): HiddenFormField<String> {
        val dummy = HiddenFormFieldImpl.Dummy(value)

        return hidden(name, dummy::data)
    }

    /**
     * Adds a hidden field
     */
    fun csrf(name: String, property: KMutableProperty0<String>) = addField(
        FormFieldImpl(_name + name, property.getter(), property.setter, { it }, { it }).csrf()
    )

    /**
     * Disables the check that looks for the presence of a csrf field
     */
    fun disableCsrfCheck() {
        csrfRequired = false
    }

    /**
     * Adds the given [FormElement] as a child
     */
    private fun <T : FormElement> addField(field: T): T = field.apply { _children.add(this) }

    /**
     * Checks that the forms security measures are set up correctly
     *
     * When
     *   security is not disabled for the form
     *   AND the form is a root form (with no parent)
     *   AND the form has no csrf field
     * Then
     *   a [InsecureFormException] will be raised
     *
     * TODO: add an option to disable the csrf check
     */
    private fun checkSecuritySetup() {

        if (!csrfRequired) {
            return
        }

        if (parent == null && !hasCsrfField()) {
            throw InsecureFormException("The form must have a csrf field")
        }
    }

    private fun hasCsrfField() = _children.filterIsInstance<CsrfFormField>().isNotEmpty()
}

fun <T : Form> T.noCsrf(): T = apply { disableCsrfCheck() }

abstract class MutatorFormBase<T : Any, M : Mutator<T>>(

    val target: M,
    name: String = "",
    parent: Form? = null

) : Form(name, parent) {

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
