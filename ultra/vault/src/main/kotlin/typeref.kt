package de.peekandpoke.ultra.vault

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType

@Suppress("unused")
data class TypeRef<T>(val type: KType) {

    companion object {

        val Any = kType<Any>()
        val AnyNull = kType<Any?>()

        val Boolean = kType<Boolean>()
        val BooleanNull = kType<Boolean?>()

        val Double = kType<Double>()
        val DoubleNull = kType<Double?>()

        val Int = kType<Int>()
        val IntNull = kType<Int?>()

        val Long = kType<Long>()
        val LongNull = kType<Long?>()

        val Number = kType<Number>()
        val NumberNull = kType<Number?>()

        val String = kType<String>()
        val StringNull = kType<String?>()
    }

    val nullable: TypeRef<T?> by lazy {
        TypeRef<T?>(
            type.classifier!!.createType(
                arguments = type.arguments,
                nullable = true
            )
        )
    }

    val list: TypeRef<List<T>> by lazy {
        TypeRef<List<T>>(
            List::class.createType(
                arguments = listOf(
                    KTypeProjection.invariant(type)
                ),
                nullable = false
            )
        )
    }

    /**
     * Wraps the current type with the given generic type [W]
     *
     * E.g. makes a String? type a MyWrapper<String?> type
     *
     * When the given type [W] does not have exactly one type parameter an exception is thrown
     */
    inline fun <reified W : Any> wrapWith(): TypeRef<W> = wrapWith(W::class, null is W)

    /**
     * Wraps the current type with the given generic type [W]
     *
     * E.g. makes a String? type a MyWrapper<String?> type
     *
     * When the given type [W] does not have exactly one type parameter an exception is thrown
     */
    @Suppress("UNCHECKED_CAST")
    fun <W : Any> wrapWith(cls: KClass<W>, nullable: Boolean): TypeRef<W> = wrapCache.getOrPut(cls) {

        if (cls.typeParameters.size != 1) {
            error("Can only wrap with a generic type with exactly one type parameter")
        }

        return TypeRef(
            cls.createType(
                arguments = listOf(
                    KTypeProjection.invariant(type)
                ),
                nullable = nullable
            )
        )
    } as TypeRef<W>


    /**
     * Internal helper val, that does not carry the correct generic type information.
     *
     * @see [unList] which takes care of the generic type information
     */
    internal val unlisted: TypeRef<Any> by lazy {
        if (type.classifier != List::class) {
            error("The current type [$this] must have the classifier List::class")
        }

        TypeRef<Any>(type.arguments[0].type!!)
    }

    private val wrapCache = mutableMapOf<KClass<*>, TypeRef<*>>()
}

/**
 * Unwraps a List-type
 *
 * E.g. makes a List<String?>-type a String?-type.
 *
 * When the classifier of the current type is not List::class an exception is thrown.
 */
@Suppress("UNCHECKED_CAST")
val <T> TypeRef<List<T>>.unList: TypeRef<T>
    get() = unlisted as TypeRef<T>


/**
 * Creates a [TypeRef] from the given class [T]
 *
 * The given type [T] must not be generic, otherwise an exception will be thrown
 */
inline fun <reified T : Any?> kType(): TypeRef<T> {

    val cls = T::class

    if (cls.typeParameters.isNotEmpty()) {
        val tr = object : TypeReference<T>() {}

        return TypeRef(tr.toKType())
    }

    return TypeRef(cls.createType(nullable = null is T))
}

/**
 * Creates a [TypeRef] from the given class
 */
fun <T : Any> Class<T>.kType(): TypeRef<T> {

    val cls = kotlin

    if (cls.typeParameters.isNotEmpty()) {
        error("Cannot create a KType for a generic class. Use the helper functions like kListType(), kMapType(), kGenericType() instead")
    }

    return TypeRef(cls.createType())
}

/**
 * Creates a [TypeRef] for List type of the given type
 */
inline fun <reified T> kListType(): TypeRef<List<T>> = kType<T>().list

inline fun <reified KEY, reified VAL> kMapType(): TypeRef<Map<KEY, VAL>> = TypeRef(
    Map::class.createType(
        arguments = listOf(
            KTypeProjection.invariant(kType<KEY>().type),
            KTypeProjection.invariant(kType<VAL>().type)
        )
    )
)


///**
// * Obtains a type reference from the context called in
// */
//inline fun <reified T> type() = object : TypeRef<T>() {}
//
///**
// * Converts a Class into a type reference
// */
//fun <T> Class<T>.asTypeRef(): TypeRef<T> = TypeRef(this)
//
//open class TypeRef<T>(private val explicitType: Type? = null) : TypeReference<T>() {
//
//    companion object {
//
//        private val AnyInst: TypeRef<Any> = type()
//
//        private val AnyNullInst: TypeRef<Any?> = type()
//
//        private val BooleanInst: TypeRef<Boolean> = type()
//
//        private val NumberInst: TypeRef<Number> = type()
//        private val NumberNullInst: TypeRef<Number?> = type()
//
//        private val StringInst: TypeRef<String> = type()
//
//        val Any get() = AnyInst
//        val AnyNull get() = AnyNullInst
//
//        val Boolean get() = BooleanInst
//
//        val Number get() = NumberInst
//        val NumberNull get() = NumberNullInst
//
//        val String get() = StringInst
//    }
//
//    /**
//     * Lazy val for the type tree
//     */
//    private val tree: TypeNode by lazy { buildTree(explicitType ?: super.getType()) }
//
//    /**
//     * Lazy val for the type
//     */
//    private val resultingType: Type by lazy { tree.type }
//
//    /**
//     * Get the type.
//     *
//     * When have the type set explicitly through the constructor, we will return this one.
//     * Otherwise we delegate to the parent class.
//     */
//    override fun getType(): Type = resultingType
//
//    /**
//     * Prints the string representation of the type.
//     */
//    override fun toString() = getType().toString()
//
//    /**
//     * Go up the "List-ladder"
//     *
//     * This will wrap the current type with another List<...>
//     *
//     * Examples:
//     *
//     * String       -> will become List<String>
//     * List<String> -> will become List<List<String>>
//     *
//     * and so on.
//     */
//    fun up(): TypeRef<List<T>> = TypeRef(
//        ParameterizedTypeImpl.make(List::class.java, arrayOf(tree.type), null)
//    )
//
//    /**
//     * Go down the Type tree
//     *
//     * This will remove the outer List<...> layer.
//     *
//     * The caller needs to specify the expected type explicitly. This is currently not the cleanest solution but a simple one.
//     *
//     * Examples:
//     *
//     * List<List<String>> -> will become List<String>
//     * List<String>       -> will become String
//     *
//     * String             -> will cause an ERROR ... so the caller must know if it is feasible to do this
//     */
//    fun <X> down(): TypeRef<X> {
//
//        if (tree.children.isEmpty()) {
//            throw VaultException("Cannot go down the tree, as the root has no children: \n$tree")
//        }
//
//        if (tree.children.size > 1) {
//            throw VaultException("Cannot go down the tree, as the root has more than one child: \n$tree")
//        }
//
//        return TypeRef(
//            ParameterizedTypeImpl.make(
//                tree.children[0].type.toClass(),
//                tree.children[0].children.map { it.type }.toTypedArray(),
//                null
//            )
//        )
//    }
//
//    /**
//     * Wraps the current type with the given type.
//     *
//     * The given type must be a parameterized type with on type parameter.
//     */
//    fun <X> wrapWith(cls: Class<X>): TypeRef<X> = TypeRef(
//        ParameterizedTypeImpl.make(cls, arrayOf(tree.type), null)
//    )
//
//    /**
//     * Wraps the current type with the given type.
//     *
//     * The given type must be a parameterized type with on type parameter.
//     */
//    inline fun <reified X> wrapWith(): TypeRef<X> = wrapWith(X::class.java)
//
//    /**
//     * Returns the resulting type as a [KType]
//     */
//    fun toKType(): KType = resultingType.toKType()
//
//    /**
//     * Internal helper for creating a [KType] from a [Type]
//     */
//    private fun Type.toKType(): KType = when (this) {
//
//        is ParameterizedType -> toClass().kotlin.createType(
//            arguments = actualTypeArguments.map {
//                KTypeProjection.invariant(it.toKType())
//            },
//            nullable = true // This is not so good. But there is currently no way to get the nullability
//        )
//
//        else -> toClass().kotlin.createType(
//            arguments = listOf(),
//            nullable = true // This is not so good. But there is currently no way to get the nullability
//        )
//    }
//
//    /**
//     * Internal helper for creating a [Class] from the a [Type]
//     */
//    private fun Type.toClass(): Class<*> = when (this) {
//        is ParameterizedType -> Class.forName(this.rawType.typeName)
//        else -> Class.forName(this.typeName)
//    }
//
//    /**
//     * Build a tree of types
//     *
//     * @see type
//     */
//    private fun buildTree(t: Type): TypeNode {
//
//        val children: List<TypeNode> = when (t) {
//            is ParameterizedType -> t.actualTypeArguments.map {
//                if (it !is WildcardType) {
//                    it
//                } else {
//                    it.upperBounds[0]
//                }
//            }
//
//            else -> listOf()
//
//        }.map { buildTree(it) }
//
//        val childTypes = children.map { it.type }.toTypedArray()
//
//        val fixedType = when {
//
//            t.typeName == Serializable::class.qualifiedName -> kotlin.Any::class.java
//
//            t is ParameterizedType -> ParameterizedTypeImpl.make(t.toClass(), childTypes, null)
//
//            else -> t
//        }
//
//        return TypeNode(fixedType, children)
//    }
//}
//
//internal class TypeNode(val type: Type, val children: List<TypeNode>)
//

/**
 * Taken from Jackson 2.9.9
 */
abstract class TypeReference<T> protected constructor() : Comparable<TypeReference<T>> {

    private val type: Type

    init {
        val superClass = javaClass.genericSuperclass

        require(superClass !is Class<*>) {
            // sanity check, should never happen
            "Internal error: TypeReference constructed without actual type information"
        }

        type = (superClass as ParameterizedType).actualTypeArguments[0]
    }

    open fun getType() = type

    /**
     * The only reason we define this method (and require implementation
     * of `Comparable`) is to prevent constructing a
     * reference without type information.
     */
    override fun compareTo(other: TypeReference<T>): Int {
        return 0
    }
    // just need an implementation, not a good one... hence ^^^

    /**
     * Returns the resulting type as a [KType]
     */
    fun toKType(): KType = type.toKType()

    /**
     * Internal helper for creating a [KType] from a [Type]
     */
    private fun Type.toKType(): KType = when (this) {

        is ParameterizedType -> toClass().createType(
            arguments = actualTypeArguments.map {
                KTypeProjection.invariant(it.toKType())
            },
            nullable = false // This is not so good. But there is currently no way to get the nullability
        )

        is WildcardType -> toClass().createType(
            arguments = upperBounds[0].toKType().arguments.map { KTypeProjection.invariant(it.type!!) },
            nullable = false // This is not so good. But there is currently no way to get the nullability
        )

        else -> toClass().createType(
            arguments = listOf(),
            nullable = false // This is not so good. But there is currently no way to get the nullability
        )
    }

    /**
     * Internal helper for creating a [KClass] from the a [Type]
     */
    private fun Type.toClass(): KClass<*> = when (this) {
        is ParameterizedType -> Class.forName(this.rawType.typeName).kotlin
        is WildcardType -> this.upperBounds[0].toClass()
        else -> Class.forName(this.typeName).kotlin
    }

}

