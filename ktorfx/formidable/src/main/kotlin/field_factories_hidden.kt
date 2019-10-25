package de.peekandpoke.ktorfx.formidable

/**
 * Adds a hidden field with a static string value
 */
fun Form.hidden(name: String, value: String): HiddenFormField<String> {

    val holder = HiddenFormFieldImpl.Holder(value)

    return add(
        FormFieldImpl(this, name, holder::data.getter(), holder::data.setter, { it }, { it }).hidden()
    )
}
