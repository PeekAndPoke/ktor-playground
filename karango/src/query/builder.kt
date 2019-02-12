package de.peekandpoke.karango.query

import de.peekandpoke.karango.IterableType
import de.peekandpoke.karango.KarangoDslMarker
import de.peekandpoke.karango.NamedType
import de.peekandpoke.karango.Statement

data class TypedQuery<T>(val query: String, val vars: Map<String, Any>, val returnType: Class<T>)

fun <T> query(builder: RootBuilder.() -> IterableType<T>): TypedQuery<T> {

    val root = RootBuilder()
    val returnType = root.builder()

    val query = QueryPrinter().append(root).build()

    return TypedQuery(query.query, query.vars, returnType.getReturnType())
}

@Suppress("FunctionName")
@KarangoDslMarker
class RootBuilder internal constructor() : Statement {

    private val stmts = mutableListOf<Statement>()

    fun <L> LET(name: String, builder: () -> L): Let<L> {

        val result = Let(name, builder())

        stmts.add(result)

        return result
    }

    fun <T, D : IterableType<T>> FOR(col: D, builder: ForLoopBuilder<T>.(D) -> IterableType<T>): IterableType<T> {

        val forLoop = ForLoopBuilder(col)
        val returnType = forLoop.builder(col)

        stmts.add(forLoop)

        return returnType
    }

    override fun print(printer: QueryPrinter) {
        printer.appendAll(stmts)
    }
}

class Let<T>(private val name: String, private val value: T) : Statement, NamedType<T> {

    override fun getSimpleName() = name
    override fun getQueryName() = "l_$name"

    override fun print(printer: QueryPrinter) {
        printer.append("LET ${getQueryName()} = ").value(getSimpleName(), value as Any).appendLine()
    }
}

@Suppress("FunctionName")
@KarangoDslMarker
class ForLoopBuilder<T> internal constructor(private val iterable: IterableType<T>) : Statement {

    internal class Op(private val keyword: String, private val inner: Statement) : Statement {
        override fun print(printer: QueryPrinter) {
            printer.append("$keyword ").append(inner).appendLine()
        }
    }

    internal class NameOf(private val named: NamedType<*>) : Statement {
        override fun print(printer: QueryPrinter) {
            printer.name(named)
        }
    }

    private val stmts = mutableListOf<Statement>()

    fun FILTER(builder: () -> Filter) = apply {
        stmts.add(Op("FILTER", builder()))
    }

    fun SORT(builder: () -> Sort) = apply {
        stmts.add(Op("SORT", builder()))
    }

    fun <TO, DO : IterableType<TO>> FOR(col: DO, builder: ForLoopBuilder<TO>.(DO) -> Unit) = apply {
        stmts.add(
            ForLoopBuilder(col).apply { builder(col) }
        )
    }

    fun LIMIT(limit: Int) = apply {
        stmts.add(Op("LIMIT", OffsetAndLimit(0, limit)))
    }

    fun LIMIT(offset: Int, limit: Int) = apply {
        stmts.add(Op("LIMIT", OffsetAndLimit(offset, limit)))
    }

    fun RETURN(iterable: IterableType<T>): IterableType<T> {
        stmts.add(Op("RETURN", NameOf(iterable)))

        return iterable
    }

    override fun print(printer: QueryPrinter) {
        printer.append("FOR ").name(iterable).append(" IN `${iterable.getSimpleName()}`").appendLine()

        printer.indent {
            appendAll(stmts)
        }
    }
}

