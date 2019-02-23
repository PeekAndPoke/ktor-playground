package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition

@Suppress("FunctionName")
interface ForBuilderTrait : BuilderTrait {

    class For(private val trait: ForBuilderTrait, private val iteratorName: String) {

        infix fun <T> IN(forIn: In<T>): TerminalExpr<T> {

            val iterator = IteratorExpr(iteratorName, forIn.iterable)
            val loop = ForLoopBuilder(iterator, forIn.iterable)
            val result = loop.(forIn.builder)(iterator)

            trait.items.add(loop)

            return result
        }
    }

    operator fun <T> Expression<List<T>>.invoke(builder: ForLoopBuilder<T>.(IteratorExpr<T>) -> TerminalExpr<T>) = In(this, builder)

    class In<T>(internal val iterable: Expression<List<T>>, internal val builder: ForLoopBuilder<T>.(IteratorExpr<T>) -> TerminalExpr<T>)

    fun FOR(iteratorName: String) = For(this, iteratorName)
}

@Suppress("FunctionName")
@KarangoDslMarker
class ForLoopBuilder<T> internal constructor(
    private val name: IteratorExpr<T>,
    private val iterable: Expression<List<T>>
) : ForBuilderTrait, Printable {

    override val items = mutableListOf<Printable>()

    fun FILTER(predicate: Expression<Boolean>): Unit = run { Filter(predicate).add() }

    fun SORT(sort: Sort): Unit = run { sort.add() }

    fun LIMIT(limit: Int): Unit = run { OffsetAndLimit(0, limit).add() }

    fun LIMIT(offset: Int, limit: Int): Unit = run { OffsetAndLimit(offset, limit).add() }

    fun RETURN(ret: Expression<T>) = Return(ret).add()

    fun INSERT(what: Expression<T>) = InsertPreStage(what)

    infix fun InsertPreStage<T>.INTO(collection: CollectionDefinition<T>)= InsertInto(what, collection).add()

    override fun printAql(p: AqlPrinter) {

        p.append("FOR ").append(name).append(" IN ").append(iterable).appendLine()

        p.indent {
            append(items)
        }
    }
}
