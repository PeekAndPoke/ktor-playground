package de.peekandpoke.mutator

typealias OnModify<T> = (newValue: T) -> Unit

interface Mutator<I : Any> {
    fun getResult(): I

    fun isModified() : Boolean
}

abstract class MutatorBase<I : Any, R : I>(private val input: I, protected val onModify: OnModify<I>) : Mutator<I> {

    private var mutableResult: R? = null

    override fun isModified() = mutableResult != null

    override fun getResult(): I = if (mutableResult != null) mutableResult!! else input

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
            copy(input)
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



