package de.peekandpoke.formidable.themes

import de.peekandpoke.formidable.FieldName
import de.peekandpoke.formidable.Form
import de.peekandpoke.formidable.GenericField
import de.peekandpoke.formidable.RenderableField
import kotlinx.html.*

open class BootstrapForm(name: String = "", parent: Form? = null) : Form(name, parent) {

    override fun <T> text(name: String, value: String, setter: (T) -> Unit, fromStr: (String) -> T): RenderableField = add { formName ->
        BootstrapTextInput(formName + name, value, setter, fromStr)
    }
}

internal class BootstrapTextInput<T>(
    name: FieldName,
    value: String,
    setter: (T) -> Unit,
    mapFromString: (String) -> T
) :
    GenericField<T>(name, value, setter, mapFromString) {

    override fun renderField(flow: FlowContent) = with(flow) {

        textInput(name = _name.value, classes = "form-control") {
            attributes["id"] = _name.toFormId
            attributes["value"] = textValue
        }
    }

    override fun renderLabel(flow: FlowContent, title: String) = with(flow) {

        label {
            attributes["for"] = _name.toFormId
            +title
        }
    }

    override fun renderErrors(flow: FlowContent) = with(flow) {

        if (errors.isNotEmpty()) {

            ul {
                errors.forEach {
                    li { +it }
                }
            }
        }
    }
}

