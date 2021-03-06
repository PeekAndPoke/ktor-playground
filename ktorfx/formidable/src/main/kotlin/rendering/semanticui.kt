package de.peekandpoke.ktorfx.formidable.rendering

import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.ktorfx.semanticui.SemanticUi
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ultra.polyglot.I18n
import kotlinx.html.*

fun <T : Form> FlowContent.formidable(i18n: I18n, form: T, configure: FORM.() -> Unit = {}, block: FormidableViewBuilder.(T) -> Any?) {

    ui.form Form {
        // default is post
        method = FormMethod.post
        action = ""

        // apply configuration to the form tag
        this.configure()

        val builder = FormidableViewBuilder(i18n, this)

        // render all hidden fields
        builder.apply {
            hiddenFields(form)
        }

        // apply the builder block
        builder.block(form)
    }
}


class FormidableViewBuilder(val i18n: I18n, val form: FORM) {

    fun FlowContent.hiddenFields(form: Form) {
        form.hiddenFields.forEach {
            hidden(it)
        }
    }

    fun <T> FlowContent.label(field: FormField<T>, label: String?) {

        if (label != null) {
            ui.given(field.hasErrors()) { error }.then Label {
                attributes["for"] = field.getId().asFormId
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
            name = field.getId().value
            value = field.textValue
        }
    }

    fun <T> FlowContent.textArea(field: FormField<T>, label: String? = null, classes: String = "") {

        ui.field.given(field.hasErrors()) { error }.then {

            label(field, label)

            textArea(classes = classes) {
                id = field.getId().asFormId
                name = field.getId().value

                +field.textValue
            }

            errors(field)
        }
    }

    fun <T> FlowContent.textInput(field: FormField<T>, label: String? = null, classes: String = "") {

        ui.field.given(field.hasErrors()) { error }.then {

            label(field, label)

            textInput(classes = classes) {
                id = field.getId().asFormId
                name = field.getId().value
                type = InputType.text
                value = field.textValue
            }

            errors(field)
        }
    }

    fun <T> FlowContent.numberInput(field: FormField<T>, label: String? = null, step: Double? = null) {

        ui.field.given(field.hasErrors()) { error }.then {

            label(field, label)

            textInput {
                id = field.getId().asFormId
                name = field.getId().value
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

            select {
                id = field.getId().asFormId
                name = field.getId().value

                field.default?.let {
                    option {
                        +i18n[it]
                    }
                }

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

    fun FlowContent.submitButton(field: ButtonField, title: String, style: SemanticUi.() -> SemanticUi = { this }) {

        ui.button.apply { style() } Submit {
            id = field.getId().asFormId
            name = field.getId().value
            value = field.value

            +title
        }
    }

    fun FlowContent.confirmButton(field: ButtonField, title: String, confirmation: String, style: SemanticUi.() -> SemanticUi = { this }) {

        ui.button.apply { style() } Submit {
            id = field.getId().asFormId
            name = field.getId().value
            value = field.value
            onClick = "return confirm(\"${confirmation.split("\"").joinToString("\\\"")}\")"

            +title
        }
    }

    fun FlowContent.confirmButton(title: String, confirmation: String, style: SemanticUi.() -> SemanticUi = { this }) {
        ui.button.apply { style() } Submit {
            onClick = "return confirm(\"${confirmation.split("\"").joinToString("\\\"")}\")"
            +title
        }
    }

    fun FlowContent.submitButton(title: String, style: SemanticUi.() -> SemanticUi = { this }) {
        ui.button.apply { style() } Submit {
            +title
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper for editable lists (remove, add via Javascript)

    fun <T, E : FormElement> FlowContent.listFieldAsGrid(
        field: MutableListField<T, E>,
        render: FlowContent.(E) -> Any?
    ) {

        val renderItem: FlowContent.(E) -> Unit = { item: E ->
            ui.column {
                listFieldItem()

                ui.top.attached.segment {
                    render(item)
                }

                ui.bottom.attached.buttons {
                    ui.icon.button {
                        listFieldRemoveAction()
                        title = "Remove Item"
                        icon.close()
                    }
                }
            }
        }

        ui.three.column.grid {
            listFieldContainer(field) { dummy -> renderItem(dummy) }

            field.forEach {
                renderItem(it)
            }

            ui.column {
                listFieldAddAction()

                ui.placeholder.raised.segment {
                    ui.icon.header {
                        icon.plus()
                    }
                }
            }
        }
    }

    fun FlowContent.listFieldItem() {
        attributes["data-formidable"] = "item"
    }

    fun FlowContent.listFieldRemoveAction() {
        attributes["data-formidable"] = "remove"
    }

    fun <T, E : FormElement> FlowContent.listFieldContainer(list: MutableListField<T, E>, block: FlowContent.(E) -> Any?) {

        attributes["data-formidable"] = "container"
        attributes["data-formidable-next-id"] = list.nextAdditionalId.toString()

        div {
            style = "display: none;"
            attributes["data-formidable"] = "dummy"

            block(
                list.dummyItem
            )
        }
    }

    fun FlowContent.listFieldAddAction() {
        attributes["data-formidable"] = "add"
    }
}

