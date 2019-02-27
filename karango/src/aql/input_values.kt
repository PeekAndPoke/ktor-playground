package de.peekandpoke.karango.aql

/**
 * Guard function to prevent call to .aql() on existing Expressions
 */
@Suppress("unused") 
@Deprecated("Cannot use .aql() on existing Expressions", level = DeprecationLevel.ERROR)
fun <T> Expression<T>.aql() : Nothing {
    throw Exception("Cannot call .aql() on already existing Expression")
}

/**
 * Helper to make any object an AQL expression
 * 
 * Usage:
 * 
 * 1.aql()
 * "string".aql()
 * true.aql()
 * Obj().aql()
 */
inline fun <reified T> T.aql(name: String = "v") = Value.of(typeRef(), this, name)

/**
 * Helper to make a "null" AQL expression
 * 
 * Usage:
 * 
 * null.aql()
 */
@Suppress("unused") fun Nothing?.aql(name: String = "v") = Value.nil(name)

data class Value<T>(private val type: TypeRef<T>, private val value: T, private val name: String) : Expression<T> {
    override fun getType() = type
    override fun printAql(p: AqlPrinter) = p.value(name, value as Any)

    companion object {
        fun <X> of(type: TypeRef<X>, value: X, name: String): Expression<X> = Value(type, value, name)

        fun nil(name: String = "v") : Expression<Any?> = NullValue(name)
        
        fun number(value: Number, name: String = "v") = of(TypeRef.Number, value, name)

        fun string(value: String, name: String = "v") = of(TypeRef.String, value, name)
    }
}

internal class NullValue(val name: String) : Expression<Any?> {
    
    override fun getType() = TypeRef.AnyNull
    
    override fun printAql(p: AqlPrinter) = p.value(name,null)
}
