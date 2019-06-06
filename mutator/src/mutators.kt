package de.peekandpoke.mutator

typealias OnModify<T> = (newValue: T) -> Unit

interface ResultHolder<I> {
    fun getResult(): I
}

abstract class MutatorBase<I : Any, R : I>(input: I, protected val onModify: OnModify<I> = {}) : ResultHolder<I> {

    private var original: I = input

    private var mutableResult: R? = null

    override fun getResult(): I = if (mutableResult != null) mutableResult!! else original

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



