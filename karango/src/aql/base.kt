package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition
import de.peekandpoke.karango.Entity

@DslMarker
annotation class KarangoDslMarker

/**
 * Helper interface for the QueryPrinter.
 *
 * When an Expression has this interface QueryPrinter::value() will use the name returned by getAlias()
 *
 * @see AqlPrinter.value()
 */
interface Aliased {
    fun getAlias(): String
}

/**
 * Base interface for all Statements.
 *
 * Statements return nothing. Statements cannot be nested within Expressions.
 */
interface Statement : Printable

/**
 * Base interface for all Expressions.
 *
 * Expression return something of a specific type T. Expressions can be nested within other Expressions.
 */
interface Expression<T> : Printable {
    /**
     * Returns a reference to the type that the expression represents.
     *
     * The type information is needed for un-serializing a query result.
     */
    fun getType(): TypeRef<T>
}

/**
 * Base interface for all terminal Expressions.
 *
 * A terminal expression can be used to create a query result cursor.
 *
 * A terminal expression ALWAYS is a list type. This is how ArangoDB returns data.
 * The returned data is always an array.
 */
interface TerminalExpr<T> : Expression<List<T>> {
    /**
     * The T within the List<T>. This type is finally used for un-serialization..
     */
    fun innerType(): TypeRef<T>
}

/**
 * Expression impl for internal usage
 */
internal class ExpressionImpl<T>(private val name_: String, private val type: TypeRef<T>) : Expression<T> {

    override fun getType() = type
    override fun printAql(p: AqlPrinter) = p.name(name_)
}

@Suppress("FunctionName")
@KarangoDslMarker
class AqlBuilder internal constructor() : ForBuilderTrait, InsertBuilderTrait, Printable {

    override val items = mutableListOf<Printable>()

    fun <T> LET(name: String, value: Expression<T>) = LetExpr(name, value).add().toExpression()

    fun LET(name: String, @Suppress("UNUSED_PARAMETER") value: Nothing?) = LET(name, NullValue())

    inline fun <reified T> LET(name: String, value: T) = Let(name, value, typeRef()).add().toExpression()

    inline fun <reified T> LET(name: String, builder: () -> T) = Let(name, builder(), typeRef()).add().toExpression()

    fun <T> RETURN(expr: Expression<T>): TerminalExpr<T> = Return(expr).add()
    
    fun <T : Entity, D : CollectionDefinition<T>> UPDATE(entity: T, col: D, builder: KeyValueBuilder<T>.(Expression<T>) -> Unit): TerminalExpr<Any> =
        UpdateDocument(
            entity,
            col,
            KeyValueBuilder<T>().apply { builder(ExpressionImpl(col.getAlias(), col.getType().down())) }
        ).add()

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
