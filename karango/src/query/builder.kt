package de.peekandpoke.karango.query

import de.peekandpoke.karango.*

data class TypedQuery<T>(val aql: String, val vars: Map<String, Any>, val returnType: Class<T>)

fun <T> query(builder: RootBuilder.() -> Statement<T>): TypedQuery<T> {

    val root = RootBuilder()
    val returnType = root.builder()

    val query = AqlPrinter().stmt(root).build()

    return TypedQuery(query.query, query.vars, returnType.getReturnType())
}

@Suppress("FunctionName")
@KarangoDslMarker
class RootBuilder internal constructor() : LetBuilderTrait, ForBuilderTrait, PrintableStatement {

    override val stmts = mutableListOf<PrintableStatement>()

    inline fun <reified T> LET(name: String, value: T): NamedExpression<T> =
        Let(name, value, T::class.java).apply { stmts.add(this) }.toExpression()

    inline fun <reified T, L : Collection<T>> LET(name: String, builder: () -> L): NamedIterableExpression<T> =
        IterableLet(name, builder(), T::class.java).apply { stmts.add(this) }.toExpression()

    inline fun <reified T> RETURN(vararg ids: String): Statement<T> =
        ReturnDocumentsByIds(ids.toList(), T::class.java).apply { stmts.add(this) }

    fun <T> RETURN(type: Class<T>, vararg ids: String): Statement<T> =
        ReturnDocumentsByIds(ids.toList(), type).apply { stmts.add(this) }

    inline fun <reified T> RETURN(collection: String, key: String) = RETURN(collection, key, T::class.java)

    fun <T> RETURN(collection: String, key: String, type: Class<T>): Statement<T> =
        ReturnDocumentById("$collection/$key", type).apply { stmts.add(this) }

    fun <T : Entity, D : CollectionDefinition<T>> UPDATE(entity: T, col: D, builder: KeyValueBuilder<T>.(D) -> Unit): Statement<T> =
        UpdateDocumentStmt(entity, col, KeyValueBuilder<T>().apply { builder(col) }).apply { stmts.add(this) }

    override fun printStmt(p: AqlPrinter) {
        p.stmts(stmts)
    }
}

@Suppress("FunctionName")
@KarangoDslMarker
class ForLoopBuilder<T> internal constructor(private val iterable: NamedIterableExpression<T>) : ForBuilderTrait, Statement<T> {

    override val stmts = mutableListOf<PrintableStatement>()

    override fun getReturnType() = iterable.getReturnType()

    fun FILTER(builder: () -> FilterPredicate) = apply { stmts.add(Filter(builder())) }

    fun SORT(builder: () -> Sort) = apply { stmts.add(builder()) }

    fun LIMIT(limit: Int) = apply { stmts.add(OffsetAndLimit(0, limit)) }

    fun LIMIT(offset: Int, limit: Int) = apply { stmts.add(OffsetAndLimit(offset, limit)) }

    fun RETURN(ret: NamedIterableExpression<T>): Statement<T> = ReturnNamed(ret, ret.getReturnType()).apply { stmts.add(this) }

    fun INSERT(what: NamedExpression<T>) = InsertPreStage(what)

    infix fun InsertPreStage<T>.INTO(collection: CollectionDefinition<T>): Statement<T> = InsertInto(what, collection).apply { stmts.add(this) }

    override fun printStmt(p: AqlPrinter) {

        p.append("FOR ").identifier(iterable).append(" IN ").name(iterable).appendLine()

        p.indent {
            stmts(stmts)
        }
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
    val stmts: MutableList<PrintableStatement>
}

@Suppress("FunctionName")
interface LetBuilderTrait : BuilderTrait {


//    fun LET(name: String, value: Int): NamedExpression<Int> = Let(name, value).apply { stmts.add(this) }.toExpression()
//
//    fun LET(name: String, value: Double): NamedExpression<Double> = Let(name, value).apply { stmts.add(this) }.toExpression()
//
//    fun LET(name: String, value: String): NamedExpression<String> = Let(name, value).apply { stmts.add(this) }.toExpression()
}

@Suppress("FunctionName")
interface ForBuilderTrait : BuilderTrait {

    fun <T, D : NamedIterableExpression<T>> FOR(col: D, builder: ForLoopBuilder<T>.(D) -> Statement<T>): Statement<T> {

        val forLoop = ForLoopBuilder(col)
        val returnType = forLoop.builder(col)

        stmts.add(forLoop)

        return returnType
    }
}

class IterableLet<T>(name: String, private val value: Collection<T>, private val type: Class<T>) : Statement<T> {

    private val expr: NamedIterableExpression<T> = NamedIterableExpressionImpl("l_$name", type)

    fun toExpression() = expr

    override fun getReturnType() = type

    override fun printStmt(p: AqlPrinter) =
        p.append("LET ").name(expr).append(" = ").value(this, value as Any).appendLine()
}

class Let<T>(name: String, private val value: T, private val type: Class<T>) : Statement<T> {

    private val expr: NamedExpression<T> = NamedExpressionImpl("l_$name", type)

    fun toExpression() = expr

    override fun getReturnType() = type

    override fun printStmt(p: AqlPrinter) =
        p.append("LET ").name(expr).append(" = ").value(this, value as Any).appendLine()
}

internal class UpdateDocumentStmt<T : Entity>(
    private val entity: T,
    private val col: CollectionDefinition<T>,
    private val kv: KeyValueBuilder<T>
) : Statement<T> {

    override fun getReturnType() = col.getReturnType()

    override fun printStmt(p: AqlPrinter) {

        with(p) {

            append("UPDATE \"").append(entity._id.ensureKey).append("\" WITH {").appendLine()

            indent {

                val pairs = kv.pairs.toMap()

                pairs.keys.forEachIndexed { idx, key ->

                    append("$key : ").value("kv", pairs.getValue(key))

                    if (idx < pairs.size - 1) {
                        append(",")
                    }

                    appendLine()
                }
            }

            append("} IN ").name(col).appendLine()
        }

    }
}
