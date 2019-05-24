package de.peekandpoke.frozen

import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

annotation class Mutator

typealias OnModify<T> = (before: T, after: T) -> Unit

abstract class DataClassMutator<T : Any>(target: T, private val onModify: OnModify<T> = { _, _ -> }) {

    var result: T = target
    var isModified = false

    operator fun plusAssign(value: T) {
        replaceResult(
            Cloner.clone(value)
        )
    }

    fun <X> modify(property: KProperty<X>, value: X) {

        makeCopy()

        property.javaField!!.apply { isAccessible = true }.set(result, value)
    }

    private fun makeCopy() {

        if (isModified) return

        replaceResult(
            Cloner.clone(result)
        )

        isModified = true
    }

    private fun replaceResult(new: T) {

        val before = result

        result = new

        onModify(before, result)
    }
}

fun <T, M> List<T>.mutator(mapper: (T, OnModify<T>) -> M, onModify: OnModify<List<T>> = { _, _ -> })
    = ListMutator(this, mapper, onModify)

class ListMutator<T, M>(
    private val target: List<T>,
    private val mapper: (T, OnModify<T>) -> M,
    private val onModify: OnModify<List<T>> = { _, _ -> }
) : Iterable<M> {

    inner class It(private val list: List<T>, private val mapper: (T, OnModify<T>) -> M) : Iterator<M> {

        private var pos = 0

        override fun hasNext() = pos < list.size

        override fun next(): M {

            val current = pos++

            return mapper(list[current]) { _, after -> modify { result[current] = after } }
        }
    }

    private var result: MutableList<T> = target.toMutableList()
    private var isChangeNotified = false

    val size get() = result.size

    override fun iterator() = It(result, mapper)

    // private helpers

    private fun <T> modify(action: () -> T): T = action().apply {

        if (!isChangeNotified) {
            isChangeNotified = true
            onModify(target, result)
        }
    }
}
