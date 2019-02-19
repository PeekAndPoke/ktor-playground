package de.peekandpoke.karango.aql

import com.fasterxml.jackson.core.type.TypeReference
import de.peekandpoke.karango.CollectionDefinition
import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.PropertyPath

@DslMarker
annotation class KarangoDslMarker

data class TypedQuery<T>(val aql: String, val vars: Map<String, Any>, val type: TypeReference<T>)

fun <T> query(builder: RootBuilder.() -> Expression<T>): TypedQuery<T> {

    val root = RootBuilder()
    val returnType = root.builder()

    val query = AqlPrinter().append(root).build()

    return TypedQuery(query.query, query.vars, returnType.getType())
}

interface Typed<T> {
    fun getType(): TypeRef<T>
}

interface Statement : Printable

interface Expression<T> : Typed<T>, Printable

interface IterableExpression<T> : Expression<T> {
    fun toIterator(): IteratorExpr<T>
}

internal class ExpressionImpl<T>(private val name_: String, private val type: TypeRef<T>) : Expression<T> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(name_)
}

internal class IterableExpressionImpl<T>(private val name_: String, private val type: TypeRef<T>) : IterableExpression<T> {

    override fun toIterator() = IteratorExpr("i_$name_", this)
    
    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(name_)
}


@Suppress("FunctionName")
@KarangoDslMarker
class RootBuilder internal constructor() : ForBuilderTrait, Printable {

    override val items = mutableListOf<Printable>()

    inline fun <reified T> LET(name: String, value: T) = ScalarLet(name, value, typeRef()).add().toExpression()

    inline fun <reified T, L : List<T>> LET(name: String, builder: () -> L) = IterableLet(name, builder(), typeRef()).add().toExpression()

    fun <T> RETURN(expr: Expression<T>) : Expression<T> = Return(expr).add()

    fun <T : Entity, D : CollectionDefinition<T>> UPDATE(entity: T, col: D, builder: KeyValueBuilder<T>.(D) -> Unit) =
        UpdateDocument(entity, col, KeyValueBuilder<T>().apply { builder(col) }).add()

    override fun printAql(p: AqlPrinter) {
        p.append(items)
    }
}


@Suppress("FunctionName")
@KarangoDslMarker
class KeyValueBuilder<T : Entity> {

    val pairs = mutableMapOf<String, Any>()

    infix fun <X> PropertyPath<T, X>.with(value: X) =
    // TODO: this does not look right here
        apply { pairs[this.getPath().joinToString("").trimStart('.')] = value as Any }

    infix fun String.with(value: Any) = apply { pairs[this] = value }
}

interface BuilderTrait {

    val items: MutableList<Printable>

    fun <T : Printable> T.add(): T = apply { items.add(this) }
}

data class IteratorExpr<T>(private val name: String, private val inner: IterableExpression<T>) : IterableExpression<T> {
    
    fun getName() : String = name
    
    override fun toIterator() = this
    
    override fun getType() = inner.getType()

    override fun printAql(p: AqlPrinter) = p.name(name)
}

data class Value(private val name: String, private val value: Any) : Expression<Any> {

    override fun getType() = TypeRef.Any

    override fun printAql(p: AqlPrinter): Any = p.value(name, value)
}

data class ArrayValue(private val name: String, private val value: List<Any>): Expression<List<Any>> {
    
    override fun getType() = typeRef<List<Any>>()

    override fun printAql(p: AqlPrinter): Any = p.value(name, value)
}

// TODO: can we get the name back ... or should we remove this one?
data class ValueExpr(private val expr: Expression<*>, private val value: Any) : Expression<Any> {

    override fun getType() = TypeRef.Any
    
    override fun printAql(p: AqlPrinter): Any = p.value("v", value)
}

