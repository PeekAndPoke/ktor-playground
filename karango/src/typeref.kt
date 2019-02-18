package de.peekandpoke.karango

import com.fasterxml.jackson.core.type.TypeReference

inline fun <reified T> typeRef() = object : TypeRef<T>() {}

open class TypeRef<T> : TypeReference<T>() {

    companion object {

        private val BooleanInst: TypeRef<Boolean> = typeRef()
        private val AnyInst: TypeRef<Any> = typeRef()

        val Boolean get() = BooleanInst
        val Any get() = AnyInst
    }
}

