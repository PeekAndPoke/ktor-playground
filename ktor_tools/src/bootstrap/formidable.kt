package io.ultra.ktor_tools.bootstrap

import io.ultra.ktor_tools.formidable.FormField
import io.ultra.ktor_tools.formidable.FormFieldWithOptions
import io.ultra.polyglot.I18n
import kotlinx.html.*

fun <T> FlowContent.label(field: FormField<T>, label: String?) {

    if (label != null) {
        label {
            attributes["for"] = field.name.asFormId
            +label
        }
    }
}

fun <T> FlowContent.errors(t: I18n, field: FormField<T>) {
    if (!field.isValid()) {

        ul {
            field.errors.forEach { li { +t[it] } }
        }
    }
}

fun <T> FlowContent.textInput(t: I18n, field: FormField<T>, label: String? = null) {

    label(field, label)

    textInput(classes = "form-control") {
        id = field.name.asFormId
        name = field.name.value
        type = InputType.text
        value = field.textValue
    }

    errors(t, field)
}

fun <T> FlowContent.numberInput(t: I18n, field: FormField<T>, label: String? = null, step: Double? = null) {

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

    errors(t, field)
}

fun <T> FlowContent.selectInput(t: I18n, field: FormFieldWithOptions<T>, label: String? = null) {

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

    errors(t, field)
}
