package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition
import de.peekandpoke.karango.Entity

@DslMarker
annotation class KarangoDslMarker

data class TypedQuery<T>(val ret: TerminalExpr<T>, val aql: String, val vars: Map<String, Any>)

fun <T> query(builder: RootBuilder.() -> TerminalExpr<T>): TypedQuery<T> {

    val root = RootBuilder()
    val ret = root.builder()

    val query = AqlPrinter().append(root).build()

    return TypedQuery(ret, query.query, query.vars)
}

interface Aliased {
    fun getAlias(): String
}

interface Typed<T> {
    fun getType(): TypeRef<T>
}

interface Statement : Printable

interface Expression<T> : Typed<T>, Printable

interface TerminalExpr<T> : Expression<List<T>> {
    fun innerType(): TypeRef<T>
}

internal class ExpressionImpl<T>(private val name_: String, private val type: TypeRef<T>) : Expression<T> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(name_)
}

@Suppress("FunctionName")
@KarangoDslMarker
class RootBuilder internal constructor() : ForBuilderTrait, BuilderTrait, Printable {

    override val items = mutableListOf<Printable>()

    inline fun <reified T> LET(name: String, value: T) = Let(name, value, typeRef()).add().toExpression()

    inline fun <reified T> LET(name: String, builder: () -> T) = Let(name, builder(), typeRef()).add().toExpression()

    fun <T> RETURN(expr: Expression<T>): Return<T> = Return(expr).add()

    fun <T : Entity, D : CollectionDefinition<T>> UPDATE(entity: T, col: D, builder: KeyValueBuilder<T>.(Expression<T>) -> Unit) =
        UpdateDocument(entity, col, KeyValueBuilder<T>()
            .apply { builder(ExpressionImpl("x", col.getType().down())) })
            .add()

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

data class IteratorExpr<T>(private val __name__: String, private val __inner__: Expression<List<T>>) : Expression<T> {

    // TODO: check me ... fix me
    override fun getType() = __inner__.getType().down<T>()

    override fun printAql(p: AqlPrinter) = p.name(__name__)
}


