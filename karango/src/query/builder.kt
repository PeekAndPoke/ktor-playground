package de.peekandpoke.karango.query

import de.peekandpoke.karango.*

data class TypedQuery<T>(val aql: String, val vars: Map<String, Any>, val returnType: Class<T>)

fun <T> query(builder: RootBuilder.() -> ReturnType<T>): TypedQuery<T> {

    val root = RootBuilder()
    val returnType = root.builder()

    val query = QueryPrinter().append(root).build()

    return TypedQuery(query.query, query.vars, returnType.getReturnType())
}

@Suppress("FunctionName")
@KarangoDslMarker
class RootBuilder internal constructor() : LetBuilderTrait, ForBuilderTrait, Statement {

    override val stmts = mutableListOf<Statement>()

    inline fun <reified T, L : Collection<T>> LET(name: String, builder: () -> L): IterableType<T> =
        IterableLet(name, builder(), T::class.java).apply { stmts.add(this) }

    inline fun <reified T> RETURN(vararg ids: String): ReturnType<T> = 
        ReturnDocumentsByIds(ids.toList(), T::class.java).apply { stmts.add(this) }
    
    fun <T> RETURN(collection: String, key: String, type: Class<T>): ReturnType<T> =
        ReturnDocumentById("$collection/$key", type).apply { stmts.add(this) }

    override fun print(printer: QueryPrinter) {
        printer.appendAll(stmts)
    }
}

@Suppress("FunctionName")
@KarangoDslMarker
class ForLoopBuilder<T> internal constructor(private val iterable: IterableType<T>) : ForBuilderTrait, Statement {

    internal class Op(private val keyword: String, private val inner: Statement) : Statement {
        override fun print(printer: QueryPrinter) {
            printer.append("$keyword ").append(inner).appendLine()
        }
    }

    internal class InsertInto(private val named: NamedType<*>, private val collection: CollectionDefinition<*>) : Statement {
        override fun print(printer: QueryPrinter) {
            printer.append("INSERT ").name(named).append(" INTO ").append("`${collection.getSimpleName()}`").appendLine()
        }
    }

    internal class NameOf(private val named: NamedType<*>) : Statement {
        override fun print(printer: QueryPrinter) {
            printer.name(named)
        }
    }

    class InsertStage<T>(val what: NamedType<T>)

    override val stmts = mutableListOf<Statement>()

    fun FILTER(builder: () -> Filter) = apply {
        stmts.add(Op("FILTER", builder()))
    }

    fun SORT(builder: () -> Sort) = apply {
        stmts.add(Op("SORT", builder()))
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

    fun INSERT(what: NamedType<T>) = InsertStage(what)

    infix fun InsertStage<T>.INTO(collection: CollectionDefinition<T>): CollectionDefinition<T> {
        stmts.add(InsertInto(what, collection))

        return collection
    }

    override fun print(printer: QueryPrinter) {

        printer.append("FOR ").name(iterable).append(" IN `${iterable.getSimpleName()}`").appendLine()

        printer.indent {
            appendAll(stmts)
        }
    }
}

interface BuilderTrait {
    val stmts: MutableList<Statement>
}

@Suppress("FunctionName")
interface LetBuilderTrait : BuilderTrait {

    fun LET(name: String, value: Int): NamedType<Int> = Let(name, value).apply { stmts.add(this) }

    fun LET(name: String, value: Double): NamedType<Double> = Let(name, value).apply { stmts.add(this) }

    fun LET(name: String, value: String): NamedType<String> = Let(name, value).apply { stmts.add(this) }
}

@Suppress("FunctionName")
interface ForBuilderTrait : BuilderTrait {

    fun <T, D : IterableType<T>> FOR(col: D, builder: ForLoopBuilder<T>.(D) -> IterableType<T>): IterableType<T> {

        val forLoop = ForLoopBuilder(col)
        val returnType = forLoop.builder(col)

        stmts.add(forLoop)

        return returnType
    }
}

class IterableLet<T>(private val name: String, private val value: Collection<T>, private val type: Class<T>) : Statement, IterableType<T> {

    override fun getSimpleName() = name
    override fun getQueryName() = "l_$name"
    override fun getReturnType() = type

    override fun print(printer: QueryPrinter) {
        printer.append("LET ${getSimpleName()} = ").value(getSimpleName(), value as Any).appendLine()
    }
}

internal class Let<T>(private val name: String, private val value: T) : Statement, NamedType<T> {

    override fun getSimpleName() = name
    override fun getQueryName() = "l_$name"

    override fun print(printer: QueryPrinter) {
        printer.append("LET ${getSimpleName()} = ").value(getSimpleName(), value as Any).appendLine()
    }
}

class ReturnDocumentById<T>(private val id: String, private val type: Class<T>) : ReturnType<T>, Statement {

    override fun getReturnType() = type

    override fun print(printer: QueryPrinter) {
        printer.append("RETURN DOCUMENT (").value("id", id).append(")").appendLine()
    }
}

class ReturnDocumentsByIds<T>(private val ids: List<String>, private val type: Class<T>) : ReturnType<T>, Statement {

    override fun getReturnType() = type

    override fun print(printer: QueryPrinter) {
        printer.append("FOR x IN DOCUMENT (").value("ids", ids).append(") RETURN x").appendLine()
    }
}

class Document<T>(private var ids: List<String>, private var type: Class<T>) : IterableType<T>, Statement {

    override fun getReturnType() = type
    override fun getSimpleName() = "doc"
    override fun getQueryName() = "doc"

    override fun print(printer: QueryPrinter) {
        printer.append("DOCUMENT (").value("ids", ids).append(")")
    }
}
