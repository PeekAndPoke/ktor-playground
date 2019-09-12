package de.peekandpoke.karango.aql

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.ICollection

@DslMarker
annotation class KarangoDslMarker

@DslMarker
annotation class KarangoFuncMarker

@DslMarker
annotation class KarangoInputMarker

@DslMarker
annotation class KarangoTypeConversionMarker

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
 * Sometimes it might be necessary to change the type of an expression
 */
inline fun <reified R : Any> Expression<*>.AS() : Expression<R> = TypeCastExpression(type(), this)

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
internal class ExpressionImpl<T>(private val name: String, private val type: TypeRef<T>) : Expression<T> {
    /**
     * The type that is represented by the expression
     */
    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(name)
}

/**
 * Internal expression impl holding the entire query
 */
internal class RootExpression<T>(private val stmts: List<Statement>, private val ret: TerminalExpr<T>) : TerminalExpr<T> {

    override fun getType() = ret.getType()

    override fun innerType() = ret.innerType()

    override fun printAql(p: AqlPrinter) = p.append(stmts).append(ret)

    companion object {
        fun <T> from(builderFun: AqlBuilder.() -> TerminalExpr<T>) : RootExpression<T> {

            val builder = AqlBuilder()
            val result : TerminalExpr<T> = builder.builderFun()

            return RootExpression(builder.stmts, result)
        }
    }
}

class TypeCastExpression<T>(private val type: TypeRef<T>, private val wrapped: Expression<*>) : Expression<T> {
    override fun getType() = type

    override fun printAql(p: AqlPrinter) = wrapped.printAql(p)
}

@Suppress("FunctionName")
@KarangoDslMarker
class AqlBuilder internal constructor() : StatementBuilder {

    override val stmts = mutableListOf<Statement>()

    @KarangoDslMarker
    fun <T> LET(name: String, value: Expression<T>) = LetExpr(name, value).addStmt().toExpression()

    @KarangoDslMarker
    fun LET(name: String, @Suppress("UNUSED_PARAMETER") value: Nothing?) = LET(name, NullValue())

    @KarangoDslMarker
    inline fun <reified T> LET(name: String, value: T) = Let(name, value, type()).addStmt().toExpression()

    @KarangoDslMarker
    inline fun <reified T> LET(name: String, builder: () -> T) = Let(name, builder(), type()).addStmt().toExpression()

    @KarangoDslMarker
    fun <T> RETURN(expr: Expression<T>): TerminalExpr<T> = Return(expr)

    // TODO: UPDATE
    fun <T : Entity, D : ICollection<T>> UPDATE(entity: T, col: D, builder: KeyValueBuilder<T>.(Expression<T>) -> Unit): TerminalExpr<Any> =
        UpdateDocument(
            entity,
            col,
            KeyValueBuilder<T>().apply { builder(ExpressionImpl(col.getAlias(), col.getType().down())) }
        )
}

@Suppress("FunctionName")
@KarangoDslMarker
class KeyValueBuilder<T : Entity> {

//    val pairs = mutableListOf<Pair<PropertyPath<*>, Any>>()
//
//    infix fun <X> PropertyPath<X>.with(value: X) = apply { pairs.add(Pair(this, value as Any)) }
}

interface StatementBuilder {

    val stmts: MutableList<Statement>

    fun <T : Statement> T.addStmt(): T = apply { stmts.add(this) }
}
