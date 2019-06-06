package de.peekandpoke.mutator

fun <T, M : ResultHolder<T>> Set<T>.mutator(onModify: OnModify<Set<T>> = {}, mapper: (T, OnModify<T>) -> M) = SetMutator(this, onModify, mapper)

internal     class MutableSetWrapper<X>(inner: MutableSet<X>) : MutableSet<X> {

    @Volatile private var _inner = inner

    @Volatile private var primed = false

    override fun toString() = _inner.toString()

    override fun equals(other: Any?) = _inner == other

    override fun hashCode() = _inner.hashCode()

    override val size: Int get() = _inner.size

    //// Ops that need reparations //////////////////////////////////////////////////////////

    override fun iterator() = repair { _inner.iterator() }

    override fun contains(element: X) = repair { _inner.contains(element) }

    override fun containsAll(elements: Collection<X>) = repair { _inner.containsAll(elements) }

    override fun isEmpty() = repair { _inner.isEmpty() }

    //// Ops that prime reparations /////////////////////////////////////////////////////////

    override fun add(element: X) = prime { _inner.add(element) }

    override fun addAll(elements: Collection<X>) = prime { _inner.addAll(elements) }

    override fun clear() = prime { _inner.clear() }

    override fun remove(element: X) = prime { _inner.remove(element) }

    override fun removeAll(elements: Collection<X>) = prime { _inner.removeAll(elements) }

    override fun retainAll(elements: Collection<X>) = prime { _inner.retainAll(elements) }

    //// helpers ////////////////////////////////////////////////////////////////////////////

    private fun <R> prime(cb: () -> R): R {

        primed = true

        return cb()
    }

    private fun <R> repair(cb: () -> R): R {

        if (primed) {
            primed = false

            // Here we restore the inner hash tables of the set
            _inner = LinkedHashSet(_inner)
        }

        return cb()
    }
}

class SetMutator<T, M : ResultHolder<T>>(

    original: Set<T>,
    onModify: OnModify<Set<T>> = {},
    private val mapper: (T, OnModify<T>) -> M

) : MutatorBase<Set<T>, MutableSet<T>>(original, onModify), Iterable<M> {

    operator fun plusAssign(value: List<M>) = plusAssign(value.map { it.getResult() }.toSet())

    override fun copy(input: Set<T>) : MutableSet<T> = MutableSetWrapper(input.toMutableSet())

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
     * Adds elements to the set
     */
    fun add(vararg element: T) = apply { getMutableResult().addAll(element) }

    /**
     * Removes elements from the set
     */
    fun remove(vararg element: T) = apply { getMutableResult().removeAll(element) }

    /**
     * Retains all elements in the list that match the filter
     */
    fun retainWhere(filter: (T) -> Boolean) = apply { plusAssign(getResult().filter(filter).toSet()) }

    /**
     * Removes all elements from the the list that match the filter
     */
    fun removeWhere(filter: (T) -> Boolean) = apply { retainWhere { !filter(it) } }

    internal inner class It(set: Set<T>, private val mapper: (T, OnModify<T>) -> M) : Iterator<M> {

        private val inner = set.toList().iterator()

        override fun hasNext() = inner.hasNext()

        override fun next(): M {

            val current = inner.next()

            return mapper(current) {
                remove(current)
                add(it)
            }
        }
    }
}
