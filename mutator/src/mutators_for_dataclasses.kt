package de.peekandpoke.mutator

import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField


abstract class DataClassMutator<T : Any>(input: T, onModify: OnModify<T> = {}) : MutatorBase<T, T>(input, onModify) {

    fun <X> modify(property: KProperty<X>, value: X) {

        property.javaField!!.apply { isAccessible = true }.set(getMutableResult(), value)
    }

    override fun copy(input: T): T = Cloner.cloneDataClass(input)
}
