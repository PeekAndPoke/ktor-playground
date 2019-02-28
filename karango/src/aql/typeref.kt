package de.peekandpoke.karango.aql

import com.fasterxml.jackson.core.type.TypeReference
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

inline fun <reified T> typeRef() = object : TypeRef<T>() {}

open class TypeRef<T>(private val explicitType: Type? = null) : TypeReference<T>() {

    companion object {

        private val AnyInst: TypeRef<Any> = typeRef()
        
        private val AnyNullInst: TypeRef<Any?> = typeRef()
        
        private val BooleanInst: TypeRef<Boolean> = typeRef()
        
        private val NumberInst: TypeRef<Number> = typeRef()
        private val NumberNullInst: TypeRef<Number?> = typeRef()
        
        private val StringInst: TypeRef<String> = typeRef()

        val Any get() = AnyInst
        val AnyNull get() = AnyNullInst
        
        val Boolean get() = BooleanInst
        
        val Number get() = NumberInst
        val NumberNull get() = NumberNullInst
        
        val String get() = StringInst
    }

    /**
     * The type ladder
     */
    private val ladder: List<Type> by lazy { buildLadder() }

    /**
     * Get the type.
     *
     * When have the type set explicitly through the constructor, we will return this one.
     * Otherwise we delegate to the parent class.
     */
    override fun getType(): Type = explicitType.let {
        when {
            it != null -> it
            else -> super.getType()
        }
    }

    /**
     * Prints the string representation of the type.
     */
    override fun toString() = type.toString()

    /**
     * Go up the "List-ladder"
     *
     * This will wrap the current type with another List<...>
     *
     * Examples:
     *
     * String       -> will become List<String>
     * List<String> -> will become List<List<String>>
     *
     * and so on.
     */
    fun up(): TypeRef<List<T>> = TypeRef(
        ParameterizedTypeImpl.make(List::class.java, arrayOf(type), null)
    )

    /**
     * Go down the "List-ladder"
     *
     * This will remove the outer List<...> layer.
     *
     * The caller needs to specify the expected type explicitly. This is currently not the cleanest solution but a simple one.
     *
     * Examples:
     *
     * List<List<String>> -> will become List<String>
     * List<String>       -> will become String
     *
     * String             -> will cause an ERROR ... so the caller must know if it is feasible to do this
     */
    fun <X> down() = TypeRef<X>(
        ParameterizedTypeImpl.make(
            Class.forName(ladder[1].typeName),
            ladder.drop(2).toTypedArray(),  // if (ladder.size > 2) arrayOf(ladder[2]) else arrayOf(),
            null
        )
    )

    /**
     * Returns a list of Types that are useful for down()
     *
     * @see down()
     */
    private fun buildLadder(): List<Type> {

        val result = mutableListOf<Type>()
        var t: Type? = type

        while (t != null) {

            when (t) {

                // We can have a class... this means we have arrived at the innermost type
                is Class<*> -> {
                    // we add it
                    result.add(t)
                    // we terminate
                    t = null
                }

                // We can have a parameterized type... this means we still have a List and did NOT yet arrive at the innermost type
                is ParameterizedType -> {
                    // we add it
                    result.add(t)
                    // we look a the first type param only
                    t = t.actualTypeArguments[0]

                    // when the first type parameter is a wildcard type we continue with the type of its upper bound
                    if (t is WildcardType) {
                        t = t.upperBounds[0]
                    }
                }

                // in all other cases we terminate
                else -> t = null
            }
        }

        return result
    }
}
