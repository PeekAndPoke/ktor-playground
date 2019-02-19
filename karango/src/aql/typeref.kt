package de.peekandpoke.karango.aql

import com.fasterxml.jackson.core.type.TypeReference

inline fun <reified T> typeRef() = object : TypeRef<T>() {}

open class TypeRef<T> : TypeReference<T>() {

    override fun toString() = type.toString()

    companion object {

        private val AnyInst: TypeRef<Any> = typeRef()
        private val BooleanInst: TypeRef<Boolean> = typeRef()
        private val StringInst: TypeRef<String> = typeRef()

        val Any get() = AnyInst
        val Boolean get() = BooleanInst
        val String get() = StringInst
    }
}

