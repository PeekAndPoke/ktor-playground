@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.ultra.common.TypeRef
import de.peekandpoke.ultra.common.nthParamName
import de.peekandpoke.ultra.common.unList

@KarangoDslMarker
fun FOR(iteratorName: String) = ForLoop.For(iteratorName)

@KarangoDslMarker
fun <T, R> FOR(iterable: Expression<List<T>>, builder: ForLoop.(Iter<T>) -> TerminalExpr<R>): TerminalExpr<R> {

    return FOR(builder.nthParamName(1)) IN (iterable.invoke(builder))
}

@KarangoDslMarker
operator fun <T, R> Expression<List<T>>.invoke(builder: ForLoop.(Iter<T>) -> TerminalExpr<R>) = ForLoop.In(this, builder)

@KarangoDslMarker
data class Iter<T>(private val __name__: String, private val __inner__: Expression<List<T>>) : Expression<T> {

    override fun getType(): TypeRef<T> = __inner__.getType().unList

    override fun printAql(p: AqlPrinter) = p.name(__name__)
}

@KarangoDslMarker
class ForLoop internal constructor() : StatementBuilder {

    @KarangoDslMarker
    class For(private val iteratorName: String) {

        @KarangoDslMarker
        infix fun <T, R> IN(forIn: In<T, R>): TerminalExpr<R> {

            val loop = ForLoop()
            val iterator = Iter(iteratorName, forIn.iterable)
            val returns = forIn.builder(loop, iterator)

            return ForLoopExpr(iterator, forIn.iterable, loop.stmts, returns)
        }
    }

    @KarangoDslMarker
    class In<T, R>(internal val iterable: Expression<List<T>>, internal val builder: ForLoop.(Iter<T>) -> TerminalExpr<R>)

    override val stmts = mutableListOf<Statement>()

    @KarangoDslMarker
    fun FILTER(predicate: Expression<Boolean>): Unit = run { Filter(predicate).addStmt() }

    @KarangoDslMarker
    fun SORT(sort: Sort): Unit = run { sort.addStmt() }

    @KarangoDslMarker
    fun <T> SORT(expr: Expression<T>, direction: Direction = Direction.ASC): Unit = SORT(expr.sort(direction))

    @KarangoDslMarker
    fun LIMIT(limit: Int): Unit = run { OffsetAndLimit(0, limit).addStmt() }

    @KarangoDslMarker
    fun LIMIT(offset: Int, limit: Int): Unit = run { OffsetAndLimit(offset, limit).addStmt() }
}

internal class ForLoopExpr<T, R>(
    private val iterator: Iter<T>,
    private val iterable: Expression<List<T>>,
    private val stmts: List<Statement>,
    private val ret: TerminalExpr<R>
) : TerminalExpr<R> {

    override fun getType() = ret.getType()

    override fun innerType() = ret.innerType()

    override fun printAql(p: AqlPrinter) = with(p) {

        append("FOR ").append(iterator).append(" IN ").append(iterable).appendLine()

        indent {
            append(stmts)

            append(ret)
        }
    }
}
