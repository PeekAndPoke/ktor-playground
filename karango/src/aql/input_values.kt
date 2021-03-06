@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.ultra.common.TypeRef
import de.peekandpoke.ultra.common.kListType
import de.peekandpoke.ultra.common.kType

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
 *
 * "string".aql()
 *
 * true.aql()
 *
 * Obj().aql()
 */
@Suppress("UNCHECKED_CAST")
@KarangoInputMarker
inline fun <reified T> T.aql(name: String = "v"): Expression<T> = when (this) {
    // guard, so we do not wrap an Expression again
    is Expression<*> -> this as Expression<T>
    // otherwise we create a value expression
    else -> Value(kType(), this, name)
}

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
 * Helper to make a "null" AQL expression
 *
 * Usage:
 *
 * null.aql()
 *
 * null.aql("some name")
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
@KarangoInputMarker
val Nothing?.aql: Expression<Any?>
    get() = this.aql()

@KarangoInputMarker
inline fun <reified T> ARRAY(vararg args: Expression<out T>): Expression<List<T>> = ArrayValue(kListType(), args.toList())

@KarangoInputMarker
inline fun <reified T> OBJECT(vararg pairs: Pair<Expression<String>, Expression<out T>>) = ObjectValue(kType(), pairs.toList())

data class Value<T>(private val type: TypeRef<T>, private val value: T, private val name: String = "v") : Expression<T> {

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

class ObjectValue<T>(private val type: TypeRef<Map<String, T>>, private val pairs: List<Pair<Expression<String>, Expression<out T>>>) :
    Expression<Map<String, T>> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = with(p) {

        append("{")

        indent {
            pairs.forEachIndexed { idx, p ->

                append(p.first).append(": ").append(p.second)

                if (idx < pairs.size - 1) {
                    append(", ")
                }
            }
        }

        append("}")
    }
}
