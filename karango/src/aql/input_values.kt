@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

/**
 * Guard function to prevent calls to .aql() on existing Expressions
 */
@Suppress("unused")
@Deprecated("Cannot use .aql() on existing Expressions", level = DeprecationLevel.ERROR)
@KarangoInputMarker
fun <T> Expression<T>.aql(): Nothing {
    throw Exception("Cannot call .aql() on already existing Expression")
}

/**
 * Guard val to prevent calls to .aql on existing Expressions
 */
@Suppress("unused")
@Deprecated("Cannot use .aql() on existing Expressions", level = DeprecationLevel.ERROR)
@KarangoInputMarker
val <T> Expression<T>.aql: Nothing
    get() {
        throw Exception("Cannot call .aql() on already existing Expression")
    }

/**
 * Helper to make any object an AQL expression
 *
 * Usage:
 *
 * 1.aql("name-in-query")
 * "string".aql()
 * true.aql()
 * Obj().aql()
 */
@Suppress("UNCHECKED_CAST")
@KarangoInputMarker
inline fun <reified T> T.aql(name: String = "v"): Expression<T> = when {
    // guard, so we do not wrap an Expression again
    this is Expression<*> -> this as Expression<T>
    // otherwise we create a value expression
    else -> Value(type(), this, name)
}

/**
 * Shorthand for converting numerical values into AQL expression
 *
 * (This is a workaround for the Kotlin compiler, as it sometimes 1.aql or similar correctly)
 *
 * Usage:
 *
 * 1.aql()
 * 1.1.aql("name-in-query")
 */
@KarangoInputMarker
fun Number.aql(name: String = "v"): Expression<Number> = aql<Number>(name)

/**
 * Shorthand for converting any object into an AQL expression without specifying a name
 *
 * Usage:
 *
 * 1.aql
 * "string".aql
 * true.aql
 * Obj().aql
 */
@KarangoInputMarker
inline val <reified T> T.aql: Expression<T>
    get() = this.aql()

/**
 * Shorthand for converting numerical values into AQL expression
 *
 * (This is a workaround for the Kotlin compiler, as it sometimes 1.aql or similar correctly)
 *
 * Usage:
 *
 * 1.aql
 * 1.1.aql
 */
@KarangoInputMarker
inline val Number.aql: Expression<Number>
    get() = this.aql()

/**
 * Helper to make a "null" AQL expression
 *
 * Usage:
 *
 * null.aql()
 */
@Suppress("unused")
@KarangoInputMarker
fun Nothing?.aql(name: String = "v"): Expression<Any?> = NullValue(name)

/**
 * Shorthand for converting a "null" into an AQL expression without specifying a name
 *
 * Usage:
 *
 * null.aql
 */
@Suppress("unused")
@KarangoInputMarker
val Nothing?.aql: Expression<Any?>
    get() = this.aql()

@KarangoInputMarker
inline fun <reified T> ARRAY(vararg args: Expression<T>): Expression<List<T>> = ArrayValue(type(), args.toList())

@KarangoInputMarker
fun <T1, T2> ARRAY(a1: E<T1>, a2: E<T2>): Expression<List<Any>> = ArrayValue(type(), listOf(a1, a2))

@KarangoInputMarker
fun <T1, T2, T3> ARRAY(a1: E<T1>, a2: E<T2>, a3: E<T3>): Expression<List<Any>> = ArrayValue(type(), listOf(a1, a2, a3))

@KarangoInputMarker
fun <T1, T2, T3, T4> ARRAY(a1: E<T1>, a2: E<T2>, a3: E<T3>, a4: E<T4>): Expression<List<Any>> = ArrayValue(type(), listOf(a1, a2, a3, a4))

@KarangoInputMarker
fun <T1, T2, T3, T4, T5> ARRAY(a1: E<T1>, a2: E<T2>, a3: E<T3>, a4: E<T4>, a5: E<T5>): Expression<List<Any>> = ArrayValue(type(), listOf(a1, a2, a3, a4, a5))

@KarangoInputMarker
fun <T1, T2, T3, T4, T5, T6> ARRAY(a1: E<T1>, a2: E<T2>, a3: E<T3>, a4: E<T4>, a5: E<T5>, a6: E<T6>): Expression<List<Any>> =
    ArrayValue(type(), listOf(a1, a2, a3, a4, a5, a6))

@KarangoInputMarker
fun <T1, T2, T3, T4, T5, T6, T7> ARRAY(a1: E<T1>, a2: E<T2>, a3: E<T3>, a4: E<T4>, a5: E<T5>, a6: E<T6>, a7: E<T7>): Expression<List<Any>> =
    ArrayValue(type(), listOf(a1, a2, a3, a4, a5, a6, a7))

@KarangoInputMarker
fun <T1, T2, T3, T4, T5, T6, T7, T8> ARRAY(a1: E<T1>, a2: E<T2>, a3: E<T3>, a4: E<T4>, a5: E<T5>, a6: E<T6>, a7: E<T7>, a8: E<T8>): Expression<List<Any>> =
    ArrayValue(type(), listOf(a1, a2, a3, a4, a5, a6, a7, a8))

@KarangoInputMarker
fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> ARRAY(a1: E<T1>, a2: E<T2>, a3: E<T3>, a4: E<T4>, a5: E<T5>, a6: E<T6>, a7: E<T7>, a8: E<T8>, a9: E<T9>): Expression<List<Any>> =
    ArrayValue(type(), listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9))

@KarangoInputMarker
fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ARRAY(a1: E<T1>, a2: E<T2>, a3: E<T3>, a4: E<T4>, a5: E<T5>, a6: E<T6>, a7: E<T7>, a8: E<T8>, a9: E<T9>, a10: E<T10>): Expression<List<Any>> =
    ArrayValue(type(), listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10))

data class Value<T>(private val type: TypeRef<T>, private val value: T, private val name: String) : Expression<T> {

    override fun getType() = type
    override fun printAql(p: AqlPrinter) = p.value(name, value as Any)
}

data class ArrayValue<T>(private val type: TypeRef<List<T>>, private val expressions: List<Expression<*>>) : Expression<List<T>> {

    override fun getType() = type
    override fun printAql(p: AqlPrinter) = p.append("[").join(expressions).append("]")
}

internal class NullValue(val name: String = "v") : Expression<Any?> {

    override fun getType() = TypeRef.AnyNull
    override fun printAql(p: AqlPrinter) = p.value(name, null)
}
