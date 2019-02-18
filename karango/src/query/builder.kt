package de.peekandpoke.karango.query

import de.peekandpoke.karango.*

data class TypedQuery<T>(val aql: String, val vars: Map<String, Any>, val returnType: Class<T>)

fun <T> query(builder: RootBuilder.() -> Expression<T>): TypedQuery<T> {

    val root = RootBuilder()
    val returnType = root.builder()

    val query = AqlPrinter().append(root).build()

    return TypedQuery(query.query, query.vars, returnType.getReturnType())
}

@Suppress("FunctionName")
@KarangoDslMarker
class RootBuilder internal constructor() : LetBuilderTrait, ForBuilderTrait, Printable {

    override val items = mutableListOf<Printable>()

    inline fun <reified T> LET(name: String, value: T): NamedExpression<T> =
        Let(name, value, T::class.java).apply { items.add(this) }.toExpression()

    inline fun <reified T, L : Collection<T>> LET(name: String, builder: () -> L): NamedIterableExpression<T> =
        IterableLet(name, builder(), T::class.java).apply { items.add(this) }.toExpression()

    fun <T> RETURN(named: NamedExpression<T>) : Expression<T> = 
        ReturnNamed(named, named.getReturnType()).apply { items.add(this) } 
    
    inline fun <reified T> RETURN(vararg ids: String): Expression<T> =
        ReturnDocumentsByIds(ids.toList(), T::class.java).apply { items.add(this) }

    fun <T> RETURN(type: Class<T>, vararg ids: String): Expression<T> =
        ReturnDocumentsByIds(ids.toList(), type).apply { items.add(this) }

    inline fun <reified T> RETURN(collection: String, key: String) = RETURN(collection, key, T::class.java)

    fun <T> RETURN(collection: String, key: String, type: Class<T>): Expression<T> =
        ReturnDocumentById("$collection/$key", type).apply { items.add(this) }

    fun <T : Entity, D : CollectionDefinition<T>> UPDATE(entity: T, col: D, builder: KeyValueBuilder<T>.(D) -> Unit): Expression<T> =
        UpdateDocument(entity, col, KeyValueBuilder<T>().apply { builder(col) }).apply { items.add(this) }

    override fun printAql(p: AqlPrinter) {
        p.append(items)
    }
}

@Suppress("FunctionName")
@KarangoDslMarker
class ForLoopBuilder<T> internal constructor(private val iterable: NamedIterableExpression<T>) : ForBuilderTrait, Expression<T> {

    override val items = mutableListOf<Printable>()

    override fun getReturnType() = iterable.getReturnType()

    fun FILTER(builder: () -> FilterPredicate) = apply { items.add(Filter(builder())) }

    fun SORT(builder: () -> Sort) = apply { items.add(builder()) }

    fun LIMIT(limit: Int) = apply { items.add(OffsetAndLimit(0, limit)) }

    fun LIMIT(offset: Int, limit: Int) = apply { items.add(OffsetAndLimit(offset, limit)) }

    fun RETURN(ret: NamedExpression<T>): Expression<T> = ReturnNamed(ret, ret.getReturnType()).apply { items.add(this) }
    
    fun RETURN(ret: NamedIterableExpression<T>): Expression<T> = ReturnIterator(ret, ret.getReturnType()).apply { items.add(this) }

    fun INSERT(what: NamedExpression<T>) = InsertPreStage(what)

    infix fun InsertPreStage<T>.INTO(collection: CollectionDefinition<T>): Expression<T> = InsertInto(what, collection).apply { items.add(this) }

    override fun printAql(p: AqlPrinter) {

        p.append("FOR ").iterator(iterable).append(" IN ").name(iterable).appendLine()

        p.indent {
            append(items)
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
    val items: MutableList<Printable>
}

@Suppress("FunctionName")
interface LetBuilderTrait : BuilderTrait {


//    fun LET(name: String, value: Int): NamedExpression<Int> = Let(name, value).apply { items.add(this) }.toExpression()
//
//    fun LET(name: String, value: Double): NamedExpression<Double> = Let(name, value).apply { items.add(this) }.toExpression()
//
//    fun LET(name: String, value: String): NamedExpression<String> = Let(name, value).apply { items.add(this) }.toExpression()
}

@Suppress("FunctionName")
interface ForBuilderTrait : BuilderTrait {

    fun <T, D : NamedIterableExpression<T>> FOR(col: D, builder: ForLoopBuilder<T>.(D) -> Expression<T>): Expression<T> {

        val forLoop = ForLoopBuilder(col)
        val returnType = forLoop.builder(col)

        items.add(forLoop)

        return returnType
    }
}

class IterableLet<T>(name: String, private val value: Collection<T>, private val type: Class<T>) : Statement {

    private val expr: NamedIterableExpression<T> = NamedIterableExpressionImpl("l_$name", type)

    fun toExpression() = expr

    override fun printAql(p: AqlPrinter) =
        p.append("LET ").name(expr).append(" = ").value(this, value as Any).appendLine()
}

class Let<T>(name: String, private val value: T, private val type: Class<T>) : Statement {

    private val expr: NamedExpression<T> = NamedExpressionImpl("l_$name", type)

    fun toExpression() = expr

    override fun printAql(p: AqlPrinter) =
        p.append("LET ").name(expr).append(" = ").value(this, value as Any).appendLine()
}

internal class UpdateDocument<T : Entity>(
    private val entity: T, private val col: CollectionDefinition<T>, private val kv: KeyValueBuilder<T>
) : Expression<T> {

    override fun getReturnType() = col.getReturnType()

    override fun printAql(p: AqlPrinter) {

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
