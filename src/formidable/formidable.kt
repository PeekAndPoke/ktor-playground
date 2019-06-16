package de.peekandpoke.formidable

import io.ktor.http.Parameters
import kotlinx.html.FlowContent
import kotlinx.html.textInput
import kotlin.reflect.KMutableProperty0

interface FormField {
    fun submit(params: Parameters)
}

interface RenderableField {
    fun render(flow: FlowContent)
}

interface FormFieldContainer {
    val children: List<FormField>
}

abstract class Form : FormField, FormFieldContainer {

    private val _children: MutableList<FormField> = mutableListOf()

    override val children: List<FormField> get() = _children

    fun <T: FormField>add(field: T) : T = field.apply { _children.add(this) }

    override fun submit(params: Parameters) {

        children.forEach { it.submit(params) }
    }
}

@JvmName("textInput_String")
fun Form.textInput(property: KMutableProperty0<String>) = add(
    TextInput(property.name, property.getter(), property.setter, { it }, { it })
)

@JvmName("textInput_String_nullable")
fun Form.textInput(property: KMutableProperty0<String?>) = add(
    TextInput(property.name, property.getter(), property.setter, { it ?: "" }, { it })
)

abstract class GenericField<T>(
    val name: String,
    var value: T,
    private val setter: (T) -> Unit,
    private val toStr: (T) -> String,
    private val fromStr: (String) -> T
) : FormField, RenderableField {

    val valAsStr get() = toStr(value)

    override fun submit(params: Parameters) {

        params[name]?.let {
            value = fromStr(it)
            setter(value)
        }
    }
}

open class TextInput<T>(name: String, value: T, setter: (T) -> Unit, mapToString: (T) -> String, mapFromString: (String) -> T) :
    GenericField<T>(name, value, setter, mapToString, mapFromString) {

    override fun render(flow: FlowContent) {
        flow.textInput(name = name) {
            attributes["value"] = valAsStr
        }
    }
}
