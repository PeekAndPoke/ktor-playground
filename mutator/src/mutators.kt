package de.peekandpoke.mutator

typealias OnModify<T> = (newValue: T) -> Unit

abstract class MutatorBase<I : Any, R : I>(input: I, protected val onModify: OnModify<I>) {

    private var original: I = input

    private var mutableResult: R? = null

    fun getResult(): I = if (mutableResult != null) mutableResult!! else original

    operator fun plusAssign(value: I) {

        if (getResult() !== value) {
            replaceResult(
                copy(value)
            )
        }
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

    protected fun <X> X.isNotSameAs(other : X) = when {

        this == null -> this != other

        (this as Any)::class.javaPrimitiveType != null -> this != other

        else -> this !== other
    }

    private fun replaceResult(new: R): R = new.apply {

        mutableResult = new

        onModify(new)
    }
}



