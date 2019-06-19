package de.peekandpoke.formidable.themes

import de.peekandpoke.formidable.FormField
import de.peekandpoke.formidable.FormFieldWithOptions
import kotlinx.html.*

fun <T> FlowContent.label(field: FormField<T>, label: String?) {

    if (label != null) {
        label {
            attributes["for"] = field.name.asFormId
            +label
        }
    }
}

fun <T> FlowContent.errors(field: FormField<T>) {
    if (!field.isValid()) {

        ul {
            field.errors.forEach { li { +it } }
        }
    }
}

fun <T> FlowContent.textInput(field: FormField<T>, label: String? = null) {

    label(field, label)

    textInput(classes = "form-control") {
        id = field.name.asFormId
        name = field.name.value
        type = InputType.text
        value = field.textValue
    }

    errors(field)
}

fun <T> FlowContent.numberInput(field: FormField<T>, label: String? = null, step: Double? = null) {

    label(field, label)

    textInput(classes = "form-control") {
        id = field.name.asFormId
        name = field.name.value
        type = InputType.number

        if (step != null) {
            this.step = step.toString()
        }

        value = field.textValue
    }

    errors(field)
}

fun <T> FlowContent.selectInput(field: FormFieldWithOptions<T>, label: String? = null) {

    label(field, label)

    select(classes = "form-control") {
        id = field.name.asFormId
        name = field.name.value

        field.options.forEach {

            option {
                value = field.mapToString(it.first)
                selected = it.first == field.value
                +it.second
            }
        }
    }

    errors(field)
}
