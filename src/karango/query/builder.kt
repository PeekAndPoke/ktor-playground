package de.peekandpoke.karango.query

import de.peekandpoke.karango.*

data class TypedQuery<T>(val query: String, val vars: Map<String, Any>, val returnType: Class<T>)

fun <T> query(builder: RootBuilder.() -> Iteratable<T>): TypedQuery<T> {

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

    fun <T, D : Iteratable<T>> FOR(col: D, builder: ForLoopBuilder<T>.(D) -> Iteratable<T>): Iteratable<T> {

        val forLoop = ForLoopBuilder<T>(col.getRealName(), col.getQueryName())
        val returnType = forLoop.builder(col)

        stmts.add(forLoop)

        return returnType
    }
    
    override fun print(printer: QueryPrinter) {
        printer.appendAll(stmts)
    }
}

class Let<T>(private val name: String, private val value: T) : Statement, Named<T> {

    override fun getRealName() = name
    override fun getQueryName() = "l_$name"

    override fun print(printer: QueryPrinter) {
        printer.append("LET ${getQueryName()} = ").value(getRealName(), value as Any).appendLine()
    }
}

@Suppress("FunctionName")
@KarangoDslMarker
class ForLoopBuilder<T> internal constructor(private val name : String, private val fqn : String) : Statement {

    internal class Op(private val keyword: String, private val inner: Statement) : Statement {
        override fun print(printer: QueryPrinter) {
            printer.append("$keyword ").append(inner).appendLine()
        }
    }

    private val stmts = mutableListOf<Statement>()

    fun FILTER(builder: () -> Filter) = apply {
        stmts.add(Op("FILTER", builder()))
    }

    fun SORT(builder: () -> Sort) = apply {
        stmts.add(Op("SORT", builder()))
    }

    fun <TO, DO : Iteratable<TO>> FOR(col: DO, builder: ForLoopBuilder<TO>.(DO) -> Unit) = apply {
        stmts.add(
            ForLoopBuilder<TO>(col.getRealName(), col.getQueryName()).apply { builder(col) }
        )
    }

    fun LIMIT(limit: Int) = apply {
        stmts.add(Op("LIMIT", OffsetAndLimit(0, limit)))
    }

    fun LIMIT(offset: Int, limit: Int) = apply {
        stmts.add(Op("LIMIT", OffsetAndLimit(offset, limit)))
    }

    fun RETURN(iterable: Iteratable<T>): Iteratable<T> {
        stmts.add(Op("RETURN", Name(fqn)))

        return iterable
    }

    override fun print(printer: QueryPrinter) {
        printer.appendLine("FOR $fqn IN $name")

        printer.indent {
            appendAll(stmts)
        }
    }
}

