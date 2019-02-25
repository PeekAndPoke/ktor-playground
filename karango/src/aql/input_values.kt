package de.peekandpoke.karango.aql


//fun Number.aql(name: String = "v") = Value.number( this, name)
//fun String.aql(name: String = "v") = Value.string(this, name)
//inline fun <reified T> List<T>.aql(name: String = "v") = Value.of(typeRef(), this, name)
inline fun <reified T> T.aql(name: String = "v") = Value.of(typeRef(), this, name)

internal data class ValueExpression(private val expr: Expression<*>, private val value: Any) : Expression<Any> {

    override fun getType() = TypeRef.Any
    override fun printAql(p: AqlPrinter): Any = p.value(expr, value)
}

data class Value<T>(private val type: TypeRef<T>, private val value: T, private val name: String) : Expression<T> {
    override fun getType() = type
    override fun printAql(p: AqlPrinter) = p.value(name, value as Any)

    companion object {
        fun <X> of(type: TypeRef<X>, value: X, name: String): Expression<X> = Value(type, value, name)

        fun number(value: Number, name: String = "v") = of(TypeRef.Number, value, name)

        fun string(value: String, name: String = "v") = of(TypeRef.String, value, name)
    }
}
