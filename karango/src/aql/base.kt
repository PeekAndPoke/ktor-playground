package de.peekandpoke.karango.aql

import com.fasterxml.jackson.core.type.TypeReference
import de.peekandpoke.karango.CollectionDefinition
import de.peekandpoke.karango.Entity

@DslMarker
annotation class KarangoDslMarker

data class TypedQuery<T>(val aql: String, val vars: Map<String, Any>, val type: TypeReference<T>)

fun <T> query(builder: RootBuilder.() -> Expression<T>): TypedQuery<T> {

    val root = RootBuilder()
    val returnType = root.builder()

    val query = AqlPrinter().append(root).build()

    return TypedQuery(query.query, query.vars, returnType.getType())
}

interface Aliased {
    fun getAlias(): String
}

interface Typed<T> {
    fun getType(): TypeRef<T>
}

interface Statement : Printable

interface Expression<T> : Typed<T>, Printable

// TODO: replace me with Expression<List<T>> or similar
interface IterableExpression<T> : Expression<T>

internal class ExpressionImpl<T>(private val name_: String, private val type: TypeRef<T>) : Expression<T> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(name_)
}

internal class IterableExpressionImpl<T>(private val name_: String, private val type: TypeRef<T>) : IterableExpression<T> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(name_)
}


@Suppress("FunctionName")
@KarangoDslMarker
class RootBuilder internal constructor() : ForBuilderTrait, BuilderTrait, Printable {

    override val items = mutableListOf<Printable>()

    inline fun <reified T> LET(name: String, value: T) = ScalarLet(name, value, typeRef()).add().toExpression()

    inline fun <reified T, L : List<T>> LET(name: String, builder: () -> L) = IterableLet(name, builder(), typeRef()).add().toExpression()

    fun <T> RETURN(expr: Expression<T>): Expression<T> = Return(expr).add()

    fun <T : Entity, D : CollectionDefinition<T>> UPDATE(entity: T, col: D, builder: KeyValueBuilder<T>.(D) -> Unit) =
        UpdateDocument(entity, col, KeyValueBuilder<T>().apply { builder(col) }).add()

    override fun printAql(p: AqlPrinter) {
        p.append(items)
    }
}


@Suppress("FunctionName")
@KarangoDslMarker
class KeyValueBuilder<T : Entity> {

    val pairs = mutableListOf<Pair<PropertyPath<*>, Any>>()

    infix fun <X> PropertyPath<X>.with(value: X) = apply { pairs.add(Pair(this, value as Any)) }
}

interface BuilderTrait {

    val items: MutableList<Printable>

    fun <T : Printable> T.add(): T = apply { items.add(this) }
}

data class IteratorExpr<T>(private val __name__: String, private val __inner__: IterableExpression<T>) : IterableExpression<T> {

    override fun getType() = __inner__.getType()

    override fun printAql(p: AqlPrinter) = p.name(__name__)
}

data class Value(private val name: String, private val value: Any) : Expression<Any> {

    override fun getType() = TypeRef.Any

    override fun printAql(p: AqlPrinter): Any = p.value(name, value)
}

data class ArrayValue(private val name: String, private val value: List<Any>) : Expression<List<Any>> {

    override fun getType() = typeRef<List<Any>>()

    override fun printAql(p: AqlPrinter): Any = p.value(name, value)
}

data class ValueExpr(private val expr: Expression<*>, private val value: Any) : Expression<Any> {

    override fun getType() = TypeRef.Any

    override fun printAql(p: AqlPrinter): Any =
        p.value(if (expr is Aliased) expr.getAlias() else "v", value)
}

