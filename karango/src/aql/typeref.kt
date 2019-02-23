package de.peekandpoke.karango.aql

import com.fasterxml.jackson.core.type.TypeReference
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

//inline fun <reified T> typeRef() =
//    object : TypeRef<T>(
//        object : TypeRef<List<T>>(
//            object : TypeRef<List<List<T>>>(
//                object : TypeRef<List<List<List<T>>>>(
//                    null
//                ) {}
//            ) {}
//        ) {}
//    ) {}

inline fun <reified T> typeRef() = object : TypeRef<T>() {}

inline fun <reified T> T.toTypeRef(): TypeRef<T> = typeRef()

open class TypeRef<T>() : TypeReference<T>() {

    constructor(type: ParameterizedType) : this() {
        this._customType = type
    }

    val children: List<ParameterizedType> by lazy { extractChildren() }

    val up: TypeRef<List<T>> by lazy {
        TypeRef<List<T>>(
            ParameterizedTypeImpl.make(List::class.java, arrayOf(type), null)
        )
    }

    fun <X> down() = TypeRef<X>(
        ParameterizedTypeImpl.make(Class.forName(children[1].typeName), arrayOf(children[2]), null)
    )

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

    private fun extractChildren(): List<ParameterizedType> {

        val result = mutableListOf<ParameterizedType>()
        var t: Type? = type

        while (t != null) {

            if (t is ParameterizedType) {

                result.add(t)

                t = t.actualTypeArguments[0]
            } else {
                t = null
            }

            if (t is WildcardType) {
                t = t.upperBounds[0]
            }
        }

        return result
    }
}
