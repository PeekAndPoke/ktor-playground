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
abstract class Form(private val name: String) : FormElement {

    /**
     * All hidden fields of the form
     */
    val hiddenFields get(): List<HiddenFormField<*>> = _children.filterIsInstance<HiddenFormField<*>>()

    /**
     * The parent of this form
     */
    private var parent: Form? = null

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
    private var csrfEnabled: Boolean = true

    init {
        if (parent == null) {
            addSubmissionCheckField()
        }
    }

    /**
     * The form name is constructed from the given name and an eventual parents form name
     */
    override fun getId(): FormElementId = parent?.let { it.getId() + name } ?: FormElementId(name)

    /**
     * Disables the check that looks for the presence of a csrf field
     */
    fun disableCsrfCheck() {
        csrfEnabled = false
    }

    /**
     * Disables the check that looks for the special hidden field that identifies that form
     */
    fun disableSubmissionCheck() {
        _children.removeAll { it is SubmissionCheckField }
    }

    /**
     * Set's the parent of the form
     */
    internal fun setParent(parent: Form) {
        this.parent = parent

        disableSubmissionCheck()
    }

    /**
     * Submits the from from by reading data from the [ApplicationCall]
     *
     * Calling this method will also acquire a csrf token from the [CsrfProtection] in the [kontainer]
     * and add a hidden csrf field to the form.
     */
    suspend fun submit(call: ApplicationCall): Boolean {

        // Attach a csrf field if
        // - this is the a root form
        // - csrf is enabled
        // - there is no csrf field present
        if (parent == null && csrfEnabled && !hasCsrfField()) {
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

        if (!checkSubmission(params)) {
            return
        }

        checkSecuritySetup()

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
     * Adds the given [FormElement] as a child
     */
    fun <T : FormElement> addElement(element: T): T = element.apply { _children.add(this) }

    /**
     * Adds a form field
     */
    fun <T, F : FormField<T>> add(field: F): F {
        if (field.parent != this) {
            error("Invalid field parent ${field.parent.name} ${field.parent}")
        }

        return addElement(field)
    }

    /**
     * Creates and adds a sub form
     */
    fun <F : Form> subForm(subForm: F): F = addElement(
        subForm.apply { setParent(this@Form) }
    )

    /**
     * Adds a hidden field
     */
    internal fun csrf(property: KMutableProperty0<String>) = add(
        FormFieldImpl(this, "_csrf_", property.getter(), property.setter, { it }, { it }).csrf()
    )

    /**
     * Checks if the special hidden field was sent along with the parameters
     */
    private fun checkSubmission(params: Parameters): Boolean {

        // The submission check is only applied to top-level forms
        if (parent != null) {
            return true
        }

        // The submission check might also be disabled manually.
        // So we try to find a the submission check field and validate it.
        // When there is no such field we return true.
        return _children.filterIsInstance<SubmissionCheckField>().firstOrNull()
            ?.isSubmitted(params)
            ?: true
    }

    /**
     * Adds a hidden field
     */
    private fun addSubmissionCheckField(): SubmissionCheckField {
        val dummy = HiddenFormFieldImpl.Holder("")

        return add(
            SubmissionCheckFieldImpl(
                FormFieldImpl(this, SubmissionCheckField.name, dummy::data.getter(), dummy::data.setter, { it }, { it })
            )
        )
    }

    /**
     * Checks that the forms security measures are set up correctly
     *
     * When
     *   security is not disabled for the form
     *   AND the form is a root form (with no parent)
     *   AND the form has no csrf field
     * Then
     *   a [InsecureFormException] will be raised
     */
    private fun checkSecuritySetup() {

        if (!csrfEnabled || parent != null) {
            return
        }

        if (!hasCsrfField()) {
            throw InsecureFormException("The form must have a csrf field")
        }
    }

    /**
     * Checks if there already is a csrf field in the children
     */
    private fun hasCsrfField() = _children.filterIsInstance<CsrfFormField>().isNotEmpty()
}

fun <T : Form> T.noCsrf(): T = apply { disableCsrfCheck() }

fun <T : Form> T.noSubmissionCheck(): T = apply { disableSubmissionCheck() }

abstract class MutatorFormBase<T : Any, M : Mutator<T>>(val target: M, name: String = "") : Form(name) {

    val isModified: Boolean get() = target.isModified()
}

abstract class MutatorForm<T : Any, M : Mutator<T>>(target: M, name: String = "") : MutatorFormBase<T, M>(target, name) {

    val result: T get() = target.getResult()
}

abstract class StorableForm<T : Any, M : Mutator<T>>(

    private val storable: Storable<T>,
    mutator: M,
    name: String = key(storable)

) : MutatorFormBase<T, M>(mutator, name) {

    val result: Storable<T> get() = storable.withValue(target.getResult())

    companion object {

        fun <T : Any> key(storable: Storable<T>): String {
            return "${storable.value::class.java.simpleName}[${storable._key}]"
        }
    }
}
