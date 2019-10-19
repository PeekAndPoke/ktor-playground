package de.peekandpoke.ktorfx.formidable.semanticui

import de.peekandpoke.ktorfx.formidable.Form
import de.peekandpoke.ktorfx.formidable.FormField
import de.peekandpoke.ktorfx.formidable.FormFieldWithOptions
import de.peekandpoke.ktorfx.formidable.HiddenFormField
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ultra.polyglot.I18n
import kotlinx.html.*

fun FlowContent.formidable(i18n: I18n, form: Form, configure: FORM.() -> Unit = {}, block: FormidableViewBuilder.() -> Unit) {

    ui.form Form {
        // default is post
        method = FormMethod.post
        action = ""

        // apply configuration to the form tag
        this.configure()

        val builder = FormidableViewBuilder(i18n, this)

        // render all hidden fields
        builder.apply {
            form.hiddenFields.forEach {
                hidden(it)
            }
        }

        // apply the builder block
        builder.block()
    }
}


class FormidableViewBuilder(private val i18n: I18n, val form: FORM) {

    fun <T> FlowContent.label(field: FormField<T>, label: String?) {

        if (label != null) {
            ui.given(field.hasErrors()) { error }.then Label {
                attributes["for"] = field.name.asFormId
                +label
            }
        }
    }

    fun <T> FlowContent.errors(field: FormField<T>) {
        if (!field.isValid()) {

            field.errors.forEach {
                ui.basic.red.pointing.label { +i18n[it] }
            }
        }
    }

    fun <T> FlowContent.hidden(field: HiddenFormField<T>) {

        if (field.hasErrors()) {
            ui.field.error {
                errors(field)
            }
        }

        input {
            type = InputType.hidden
            name = field.name.value
            value = field.textValue
        }
    }

    fun <T> FlowContent.textArea(field: FormField<T>, label: String? = null) {

        ui.field.given(field.hasErrors()) { error }.then {

            label(field, label)

            textArea(classes = "form-control") {
                id = field.name.asFormId
                name = field.name.value

                +field.textValue
            }

            errors(field)
        }
    }

    fun <T> FlowContent.textInput(field: FormField<T>, label: String? = null) {

        ui.field.given(field.hasErrors()) { error }.then {

            label(field, label)

            textInput(classes = "form-control") {
                id = field.name.asFormId
                name = field.name.value
                type = InputType.text
                value = field.textValue
            }

            errors(field)
        }
    }

    fun <T> FlowContent.numberInput(field: FormField<T>, label: String? = null, step: Double? = null) {

        ui.field.given(field.hasErrors()) { error }.then {

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
    }

    fun <T> FlowContent.selectInput(field: FormFieldWithOptions<T>, label: String? = null) {

        ui.field.given(field.hasErrors()) { error }.then {

            label(field, label)

            select(classes = "form-control") {
                id = field.name.asFormId
                name = field.name.value

                field.options.forEach {

                    option {
                        value = field.mapToView(it.first)

                        selected = value == field.textValue

                        +i18n[it.second]
                    }
                }
            }

            errors(field)
        }
    }
}

