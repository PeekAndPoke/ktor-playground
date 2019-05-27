package de.peekandpoke.mutator

import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

typealias OnModify<T> = (newValue: T) -> Unit

abstract class MutatorBase<I : Any, R : I>(input: I, private val onModify: OnModify<I> = {}) {

    private var original: I = input

    private var mutableResult: R? = null

    fun getResult() : I = if (mutableResult != null) mutableResult!! else original

    operator fun plusAssign(value: I) {

        replaceResult(
            copy(value)
        )
    }

    protected abstract fun copy(input: I): R

    protected fun getMutableResult(): R {

        if (mutableResult != null) {
            return mutableResult!!
        }

        return replaceResult(
            copy(original)
        )
    }

    private fun replaceResult(new: R): R = new.apply {

        mutableResult = new

        onModify(new)
    }
}

abstract class DataClassMutator<T : Any>(input: T, onModify: OnModify<T> = {}) : MutatorBase<T, T>(input, onModify) {

    fun <X> modify(property: KProperty<X>, value: X) {

        property.javaField!!.apply { isAccessible = true }.set(getMutableResult(), value)
    }

    override fun copy(input: T): T = Cloner.cloneDataClass(input)
}

fun <T, M> List<T>.mutator(onModify: OnModify<List<T>> = {}, mapper: (T, OnModify<T>) -> M) = ListMutator(this, onModify, mapper)


class ListMutator<T, M>(original: List<T>, onModify: OnModify<List<T>> = {}, private val mapper: (T, OnModify<T>) -> M) :
    MutatorBase<List<T>, MutableList<T>>(original, onModify), Iterable<M> {

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
     * Add an element at the end of the list
     */
    fun push(element: T) = apply { getMutableResult().add(element) }

    /**
     * Add an element at the beginning of the list
     */
    fun unshift(element: T) = apply { getMutableResult().add(0, element) }

    /**
     * Get the element at the given index
     */
    operator fun get(index: Int) = mapper(getMutableResult()[index]) { set(index, it) }

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
