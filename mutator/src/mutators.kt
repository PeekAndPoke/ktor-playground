package de.peekandpoke.mutator

import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

typealias OnModify<T> = (newValue: T) -> Unit

abstract class MutatorBase<I : Any, R : I>(input: I, private val onModify: OnModify<I> = {}) {

    private var original: I = input

    private var mutableResult: R? = null

    fun getResult(): I = if (mutableResult != null) mutableResult!! else original

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

fun <T, M> Set<T>.mutator(onModify: OnModify<Set<T>> = {}, mapper: (T, OnModify<T>) -> M) = SetMutator(this, onModify, mapper)

class SetMutator<T, M>(original: Set<T>, onModify: OnModify<Set<T>> = {}, private val mapper: (T, OnModify<T>) -> M) :
    MutatorBase<Set<T>, MutableSet<T>>(original, onModify), Iterable<M> {

    override fun copy(input: Set<T>) = input.toMutableSet()

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
     * Adds an element to the set
     */
    fun add(element: T) = apply { getMutableResult().add(element) }

    /**
     * Removes an element from the set
     */
    fun remove(element: T) = apply { getMutableResult().remove(element) }

    internal inner class It(set: Set<T>, private val mapper: (T, OnModify<T>) -> M) : Iterator<M> {

        private val inner = set.toList().iterator()

        override fun hasNext() = inner.hasNext()

        override fun next(): M {

            val current = inner.next()

            return mapper(current) { remove(current).add(it) }
        }
    }
}

fun <T, K, M> Map<K, T>.mutator(onModify: OnModify<Map<K, T>> = {}, mapper: (T, OnModify<T>) -> M) = MapMutator(this, onModify, mapper)

class MapMutator<T, K, M>(original: Map<K, T>, onModify: OnModify<Map<K, T>> = {}, private val mapper: (T, OnModify<T>) -> M) :
    MutatorBase<Map<K, T>, MutableMap<K, T>>(original, onModify), Iterable<M> {

    override fun copy(input: Map<K, T>) = input.toMutableMap()

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
     * Get the element at the given index
     */
    operator fun get(index: K): M? = getMutableResult()[index]?.let { entry -> mapper(entry) { set(index, it) } }

    /**
     * Set the element at the given index
     */
    operator fun set(index: K, element: T) = apply { getMutableResult()[index] = element }

    internal inner class It(map: Map<K, T>, private val mapper: (T, OnModify<T>) -> M) : Iterator<M> {

        private val inner = map.iterator()

        override fun hasNext() = inner.hasNext()

        override fun next(): M {

            val next = inner.next()

            return mapper(next.value) { set(next.key, it) }
        }
    }
}
