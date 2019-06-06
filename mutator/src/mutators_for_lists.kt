package de.peekandpoke.mutator

import de.peekandpoke.ultra.common.pop
import de.peekandpoke.ultra.common.push
import de.peekandpoke.ultra.common.shift
import de.peekandpoke.ultra.common.unshift

fun <T, M : ResultHolder<T>> List<T>.mutator(onModify: OnModify<List<T>> = {}, mapper: (T, OnModify<T>) -> M): ListMutator<T, M> {

    return ListMutator(this, onModify, mapper, { it.getResult() })
}

@JvmName("mutator_string")
fun List<String>.mutator(onModify: OnModify<List<String>> = {}, mapper: (String, OnModify<String>) -> String): ListMutator<String, String> {

    return ListMutator(this, onModify, mapper, { it })
}

class ListMutator<T, M>(

    original: List<T>,
    onModify: OnModify<List<T>> = {},
    private val mapper: (T, OnModify<T>) -> M,
    private val backwardMapper: (M) -> T

) : MutatorBase<List<T>, MutableList<T>>(original, onModify), Iterable<M> {

    operator fun plusAssign(value: List<M>) = plusAssign(value.map(backwardMapper))

    override fun copy(input: List<T>) = input.toMutableList()

    override fun iterator(): Iterator<M> = It(getMutableResult(), mapper)

    /**
     * Returns the size of the list
     */
    val size get() = getResult().size

    /**
     * Returns true when the list is empty
     */
    fun isEmpty() = getResult().isEmpty()

    /**
     * Clears the whole list
     */
    fun clear() = apply { getMutableResult().clear() }

    /**
     * Add an element at the end of the list
     */
    fun push(vararg element: T) = apply { getMutableResult().push(*element) }

    /**
     * Removes the last element from the list and returns it
     */
    fun pop(): T = getMutableResult().pop()

    /**
     * Add an element at the beginning of the list
     */
    fun unshift(vararg element: T) = apply { getMutableResult().unshift(*element) }

    /**
     * Removes the first element from the list and returns it
     */
    fun shift(): T = getMutableResult().shift()

    /**
     * Removes a specific element from the list
     */
    fun remove(vararg element: T) = apply { getMutableResult().removeAll(element) }

    /**
     * Removes the element at the given index
     */
    fun removeAt(index: Int) = apply { getMutableResult().removeAt(index) }

    /**
     * Retains all elements in the list that match the filter
     */
    fun retainWhere(filter: (T) -> Boolean) = apply { plusAssign(getResult().filter(filter)) }

    /**
     * Removes all elements from the the list that match the filter
     */
    fun removeWhere(filter: (T) -> Boolean) = apply { retainWhere { !filter(it) } }

    /**
     * Get the element at the given index
     */
    operator fun get(index: Int): M = mapper(getMutableResult()[index]) { set(index, it) }

    /**
     * Set the element at the given index
     */
    operator fun set(index: Int, element: T) = apply { getMutableResult()[index] = element }

    internal inner class It(private val list: List<T>, private val mapper: (T, OnModify<T>) -> M) : Iterator<M> {

        private var pos = 0

        override fun hasNext() = pos < list.size

        override fun next(): M {

            val current = pos++

            return mapper(list[current]) { set(current, it) }
        }
    }
}
