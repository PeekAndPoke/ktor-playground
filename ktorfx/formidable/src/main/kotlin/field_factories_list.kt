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

/**
 * Internal helper class that hosts List elements in [ListField] or [MutableListField]
 */
internal class ListElementForm(name: String) : Form(name)

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

    protected val list = prop.getter()

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
}

open class MutableListField<T, E : FormElement>(
    prop: KProperty0<MutableList<T>>,
    elementBuilder: Form.(ListItem<T>) -> E,
    private val dummyProvider: () -> T
) : StaticListField<T, E>(prop, elementBuilder) {

    /**
     * Get the ID of the first additional field.
     *
     * This value is increased by [addAdditionalItems]
     * This value is picked up by javascript.
     */
    val nextAdditionalId get() = _nextAdditionalId

    /**
     * private backing field for [nextAdditionalId]
     */
    private var _nextAdditionalId = 1

    /**
     * The dummy item is invisibly rendered into the page.
     */
    val dummyItem by lazy {

        val tmp = subForm(
            ListElementForm("[DUMMY]")
        )

        tmp.elementBuilder(
            ListItem({ dummyProvider() }, {})
        )
    }

    /**
     * Special handling for submitting this list field
     */
    override fun submit(params: Parameters) {

        removeRemovedItems(params)

        // The user is able to add items to the list.
        // We have to reflect these changes by adding these items to the list.
        addAdditionalItems(params)

        // Submit to the items
        super.submit(params)
    }

    /**
     * Removes list items that where removed by this user.
     *
     * The user is able to remove items from the list.
     * We have to reflect these changes by removing these items from the list.
     */
    private fun removeRemovedItems(params: Parameters) {

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

    /**
     * Adds list items for the items that where added by the user.
     *
     * The user is able to add items to the list.
     * We have to reflect these changes by adding these items to the list.
     */
    private fun addAdditionalItems(params: Parameters) {

        val names = params.names()

        val id = this.getId().value

        // Get the names of all additional items
        val matchedNames = names
            // Only get the parameters that belong to this exact list field
            .filter { it.startsWith("$id.[ADD-") }
            // Try to filter out the additional IDs
            .mapNotNull {

                val match = "\\[ADD-(.+)]".toRegex().find(it)

                match?.groupValues?.get(1)?.toIntOrNull()
            }
            .distinct()

        // Update the nextAdditionalId depending on what we found above
        _nextAdditionalId = (matchedNames.max() ?: 0) + 1

        // Create the additional items from the matched names
        val additionalItems = matchedNames.map { matchedName ->

            val tmp = subForm(
                ListElementForm("[ADD-$matchedName]")
            )

            // Add a new item to list
            list.add(dummyProvider())
            // The index of the new item
            val idx = list.size - 1

            // Create the child element
            tmp.elementBuilder(
                ListItem(
                    { list[idx] },
                    { list[idx] = it }
                )
            )
        }

        // Add the additional items to our items field.
        // This is needed to be able to [submit] to these newly created fields
        items.addAll(additionalItems)
    }
}



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

fun <T, E : FormElement> Form.list(
    prop: KProperty0<MutableList<T>>,
    dummyProvider: () -> T,
    elementBuilder: Form.(ListItem<T>) -> E
): MutableListField<T, E> {

    return subForm(
        MutableListField(
            prop = prop,
            elementBuilder = elementBuilder,
            dummyProvider = dummyProvider
        )
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
