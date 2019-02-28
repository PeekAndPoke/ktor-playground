package de.peekandpoke.karango.aql

/**
 * Guard function to prevent calls to .aql() on existing Expressions
 */
@Suppress("unused")
@Deprecated("Cannot use .aql() on existing Expressions", level = DeprecationLevel.ERROR)
fun <T> Expression<T>.aql(): Nothing {
    throw Exception("Cannot call .aql() on already existing Expression")
}

/**
 * Guard val to prevent calls to .aql on existing Expressions
 */
@Suppress("unused")
@Deprecated("Cannot use .aql() on existing Expressions", level = DeprecationLevel.ERROR)
val <T> Expression<T>.aql: Nothing get() {
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
inline fun <reified T> T.aql(name: String = "v") : Expression<T> = when {
    // guard, so we do not wrap an Expression again
    this is Expression<*> -> this as Expression<T>
    // otherwise we create a value expression
    else -> Value(typeRef(), this, name)
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
fun Number.aql(name: String = "v") : Expression<Number> = aql<Number>(name)

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
inline val <reified T> T.aql : Expression<T> get() = this.aql()

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
inline val Number.aql : Expression<Number> get() = this.aql()

/**
 * Helper to make a "null" AQL expression
 *
 * Usage:
 *
 * null.aql()
 */
@Suppress("unused") fun Nothing?.aql(name: String = "v") : Expression<Any?> = NullValue(name)

/**
 * Shorthand for converting a "null" into an AQL expression without specifying a name
 * 
 * Usage:
 *
 * null.aql
 */
@Suppress("unused") val Nothing?.aql : Expression<Any?> get() = this.aql()

data class Value<T>(private val type: TypeRef<T>, private val value: T, private val name: String) : Expression<T> {

    override fun getType() = type
    override fun printAql(p: AqlPrinter) = p.value(name, value as Any)
}

internal class NullValue(val name: String = "v") : Expression<Any?> {

    override fun getType() = TypeRef.AnyNull
    override fun printAql(p: AqlPrinter) = p.value(name, null)
}
