package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.polyglot.Translatable
import io.ktor.http.Parameters

fun Form.button(name: String, buttonValue: String = name): ButtonField {

    return addElement(
        ButtonField(this, name, buttonValue)
    )
}

class ButtonField(
    override val parent: Form,
    val name: String,
    private val buttonValue: String
) : FormField<String> {

    private var _textValue: String = ""

    /**
     * Returns true when the button value was sent, this means that the button was clicked to submit the form
     */
    val isClicked get() = _textValue == buttonValue

    override fun getId(): FormElementId = parent.getId() + name

    override fun submit(params: Parameters) {
        val id = getId().value

        _textValue = params[id] ?: ""
    }

    override val value: String = buttonValue

    override val textValue: String = _textValue

    override val errors: List<Translatable> = listOf()

    override fun mapToView(value: String): String = buttonValue

    override fun addMapper(mapper: (String) -> String): FormField<String> = apply {
        // no mappers for buttons
    }

    override fun addAcceptRule(errorMessage: Translatable, rule: AcceptRule): FormField<String> = apply {
        // no rules for buttons
    }

    override fun addResultRule(errorMessage: Translatable, rule: Rule<String>): FormField<String> = apply {
        // no rules for buttons
    }
}
