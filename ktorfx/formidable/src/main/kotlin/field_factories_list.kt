package de.peekandpoke.ktorfx.formidable

import io.ktor.http.Parameters
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Helpers for comma separates lists
/////

/**
 * Adds a field for list and displays the values as a comma separated list
 */
@JvmName("separatedListField_List")
fun <T> Form.separatedListField(
    prop: KMutableProperty0<List<T>>,
    toStr: (T) -> String,
    fromStr: (String) -> T,
    separator: String
): FormField<List<T>> = add(
    FormFieldImpl(
        parent = this,
        prop = prop,
        toStr = { it.joinToString(separator, transform = toStr) },
        fromStr = { str -> str.split(separator).map(String::trim).filter(String::isNotEmpty).map(fromStr) }
    )
)


/**
 * Adds a field for list and displays the values as a comma separated list
 */
@JvmName("separatedListField_List?")
fun <T> Form.separatedListField(
    prop: KMutableProperty0<List<T>?>,
    toStr: (T) -> String,
    fromStr: (String) -> T,
    separator: String
): FormField<List<T>?> = add(
    FormFieldImpl(
        parent = this,
        prop = prop,
        toStr = { it?.joinToString(separator, transform = toStr) ?: "" },
        fromStr = { str -> str.split(separator).map(String::trim).filter(String::isNotEmpty).map(fromStr) }
    )
)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Helpers for real lists
/////

class ListField<T : FormElement>(private val name: String, private val parent: Form, val children: List<T>) : FormElement, Iterable<T> {

    override fun getId(): FormElementId = parent.getId() + name

    override fun iterator(): Iterator<T> = children.iterator()

    override fun submit(params: Parameters) {
        forEach {
            it.submit(params)
        }
    }

    override fun isValid(): Boolean = all { it.isValid() }
}

open class StaticListField<T, E : FormElement>(
    prop: KProperty0<MutableList<T>>,
    protected val elementBuilder: Form.(ListItem<T>) -> E
) : Form(prop.name), Iterable<E> {

    private val list = prop.getter()

    val items = list.mapIndexed { idx, _ ->
        // Create a temp form to propagate the field name
        val tmp = subForm(
            ListElementForm(idx.toString())
        )

        // build the child element
        tmp.elementBuilder(
            ListItem(
                { list[idx] },
                { list[idx] = it }
            )
        )
    }.toMutableList()

    override fun submit(params: Parameters) {

        // The user is able to remove items from the list.
        // We have to reflect these changes by removing the form items from the list
        removeItems(params)

        // submit all the param to all items
        items.forEach {
            it.submit(params)
        }
    }

    override fun isValid(): Boolean {
        return items.all {
            it.isValid()
        }
    }

    override fun iterator(): Iterator<E> = items.iterator()

    private fun removeItems(params: Parameters) {

        val paramNames = params.names()

        val indexesToRemove = mutableListOf<Int>()

        // Find all the sub-form / sub-elements that have not been submitted.
        // We assume that sub-form that are not submitted where removed by the user (via javascript).
        items.forEachIndexed { idx, child ->

            val childId = child.getId().value

            if (paramNames.none { it.startsWith(childId) }) {
                indexesToRemove.add(idx)
            }
        }

        indexesToRemove
            .reversed() // IMPORTANT: We reverse the order of the indexes. So we can safely remove by index from back to front.
            .forEach { idx ->
                // remove the items that have not been submitted
                items.removeAt(idx)
                // remove item from the list
                list.removeAt(idx)
            }
    }
}

open class MutableListField<T, E : FormElement>(
    prop: KProperty0<MutableList<T>>,
    elementBuilder: Form.(ListItem<T>) -> E,
    private val dummy: T
) : StaticListField<T, E>(prop, elementBuilder) {

    val dummyItem by lazy {

        val tmp = subForm(
            ListElementForm("[DUMMY]")
        )

        tmp.elementBuilder(
            ListItem({ dummy }, {})
        )
    }
}


internal class ListElementForm(name: String) : Form(name)

class ListItem<T>(
    private val getter: () -> T,
    private val setter: (T) -> Unit
) {
    var value: T
        get() = getter()
        set(value) = setter(value)
}

fun <T, E : FormElement> Form.list(prop: KProperty0<MutableList<T>>, elementBuilder: Form.(ListItem<T>) -> E): StaticListField<T, E> {

    return subForm(
        StaticListField(prop = prop, elementBuilder = elementBuilder)
    )
}

fun <T, E : FormElement> Form.list(prop: KProperty0<MutableList<T>>, dummy: T, elementBuilder: Form.(ListItem<T>) -> E): MutableListField<T, E> {

    return subForm(
        MutableListField(prop = prop, elementBuilder = elementBuilder, dummy = dummy)
    )
}

fun <T, E : FormElement> Form.list(prop: KMutableProperty0<List<T>>, elementBuilder: Form.(ListItem<T>) -> E): ListField<E> {

    val list = prop.getter().toMutableList()

    return addElement(
        ListField(
            name = prop.name,
            parent = this,
            children = list.mapIndexed { idx, _ ->
                // Create a temp form to propagate the field name
                val tmp = ListElementForm(prop.name + "." + idx.toString()).apply { setParent(this@list) }

                // build the child element
                tmp.elementBuilder(
                    ListItem(
                        { list[idx] },
                        {
                            list[idx] = it
                            prop.set(list)
                        }
                    )
                )
            }
        )
    )
}
