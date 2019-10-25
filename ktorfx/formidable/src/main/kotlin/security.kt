package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.security.csrf.CsrfProtection

/**
 * Adds a hidden csrf token field to the form
 */
fun Form.csrf(csrf: CsrfProtection): FormField<String> {

    data class CsrfTokenHolder(var token: String)

    val salt = getId().value

    val dummy = CsrfTokenHolder(csrf.createToken(salt))

    return csrf(dummy::token)
        .addAcceptRule(FormidableI18n.invalid_csrf_token) { value: String -> csrf.validateToken(salt, value) }
}

/**
 * Converts a [FormField] into a [CsrfFormField]
 */
internal fun FormField<String>.csrf(): CsrfFormField = CsrfFormFieldImpl(this)

/**
 * Internal implementation of [CsrfFormField]
 */
internal class CsrfFormFieldImpl(
    private val wrapped: FormField<String>
) : CsrfFormField, FormField<String> by wrapped {

    /**
     * The csrf field is special field that
     * will ALWAYS show the initial value of the field
     * and will NEVER show the data sent by the user
     */
    override val textValue: String get() = wrapped.value
}
