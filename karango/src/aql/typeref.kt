package de.peekandpoke.karango.aql

import com.fasterxml.jackson.core.type.TypeReference
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

inline fun <reified T> typeRef() = object : TypeRef<T>() {}

inline fun <reified T> T.toTypeRef(): TypeRef<T> = typeRef()

open class TypeRef<T>() : TypeReference<T>() {

    constructor(type: ParameterizedType) : this() {
        this._customType = type
    }

    val up: TypeRef<List<T>> by lazy {
        TypeRef<List<T>>(
            ParameterizedTypeImpl.make(List::class.java, arrayOf(type), null)
        )
    }

    private var _customType: Type? = null

    override fun getType(): Type {

        val t = _customType

        return when {
            t != null -> t
            else -> super.getType()
        }
    }

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
