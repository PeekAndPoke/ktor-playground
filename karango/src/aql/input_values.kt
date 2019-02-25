package de.peekandpoke.karango.aql


fun Number.aql(name: String = "v"): Expression<Number> = NumberValue(name, this)
fun String.aql(name: String = "v"): Expression<String> = StringValue(name, this)
fun <T> List<T>.aql(name: String = "v"): Expression<List<T>> = ArrayValue(name, this, typeRef())

internal data class ValueExpression(private val expr: Expression<*>, private val value: Any) : Expression<Any> {

    override fun getType() = TypeRef.Any

    override fun printAql(p: AqlPrinter): Any =
        p.value(if (expr is Aliased) expr.getAlias() else "v", value)
}

internal data class ArrayValue<T>(private val name: String, private val value: List<T>, private val type: TypeRef<List<T>>) : Expression<List<T>> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter): Any = p.value(name, value)
}

internal data class NumberValue(private val name: String, private val value: Number) : Expression<Number> {

    override fun getType() = TypeRef.Number

    override fun printAql(p: AqlPrinter): Any = p.value(name, value)
}

internal data class StringValue(private val name: String, private val value: String) : Expression<String> {

    override fun getType() = TypeRef.String

    override fun printAql(p: AqlPrinter): Any = p.value(name, value)
}
