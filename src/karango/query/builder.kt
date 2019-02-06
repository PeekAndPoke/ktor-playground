package de.peekandpoke.karango.query

import de.peekandpoke.karango.CollectionDef
import de.peekandpoke.karango.KarangoDslMarker

data class TypedQuery<T>(val query: String, val vars: Map<String, Any>, val returnType: ReturnType<T>)

fun <T : Any> query(builder: RootBuilder.() -> ReturnType<T>): TypedQuery<T> {

    val root = RootBuilder()
    val returnType = root.builder()

    val query = QueryPrinter().append(root).build()

    return TypedQuery(query.query, query.vars, returnType)
}

@Suppress("FunctionName")
@KarangoDslMarker
class RootBuilder internal constructor() : Expression {

    private val stmts = mutableListOf<Expression>()

    fun <T : Any, D : CollectionDef<T>> FOR(col: D, builder: ForLoopBuilder<T, D>.(D) -> ReturnType<T>): ReturnType<T> {

        val forLoop = ForLoopBuilder(col)
        val returnType = forLoop.builder(col)

        stmts.add(forLoop)

        return returnType
    }

    override fun print(printer: QueryPrinter) {
        printer.appendAll(stmts)
    }
}

@Suppress("FunctionName")
@KarangoDslMarker
class ForLoopBuilder<T : Any, D : CollectionDef<T>> internal constructor(private val def: D) : Expression {

    internal class Op(private val keyword: String, private val inner: Expression) : Expression {
        override fun print(printer: QueryPrinter) {
            printer.append("$keyword ").append(inner).appendLine()
        }
    }

    private val stmts = mutableListOf<Expression>()

    fun FILTER(builder: () -> Filter) = apply {
        stmts.add(Op("FILTER", builder()))
    }

    fun SORT(builder: () -> Sort) = apply {
        stmts.add(Op("SORT", builder()))
    }

    fun <TO : Any, DO : CollectionDef<TO>> FOR(col: DO, builder: ForLoopBuilder<TO, DO>.(DO) -> Unit) = apply {
        stmts.add(ForLoopBuilder(col).apply { builder(col) })
    }

    fun LIMIT(limit: Int) = apply {
        stmts.add(Op("LIMIT", OffsetAndLimit(0, limit)))
    }

    fun LIMIT(offset: Int, limit: Int) = apply {
        stmts.add(Op("LIMIT", OffsetAndLimit(offset, limit)))
    }

    fun RETURN(): ReturnType<T> {
        stmts.add(Op("RETURN", Name(def.fqn)))

        return ReturnType(def.stores.java)
    }

    override fun print(printer: QueryPrinter) {
        printer.appendLine("FOR ${def.fqn} IN ${def.getCollectionName()}")

        printer.indent {
            appendAll(stmts)
        }
    }
}

