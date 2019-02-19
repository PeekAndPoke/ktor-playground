package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition

@Suppress("FunctionName")
interface ForBuilderTrait : BuilderTrait {

    fun <T, D : IterableExpression<T>> FOR(col: D, builder: ForLoopBuilder<T>.(IteratorExpr<T>) -> Expression<T>): Expression<T> {

        val forLoop = ForLoopBuilder(col.toIterator(), col)
        val returnType = forLoop.builder(col.toIterator())

        items.add(forLoop)

        return returnType
    }
}

@Suppress("FunctionName")
@KarangoDslMarker
class ForLoopBuilder<T> internal constructor(
    private val name: IteratorExpr<T>,
    private val iterable: IterableExpression<T>
) : ForBuilderTrait, Expression<T> {

    override val items = mutableListOf<Printable>()

    override fun getType() = iterable.getType()

    fun FILTER(builder: () -> Expression<Boolean>) = apply { items.add(Filter(builder())) }

    fun SORT(builder: () -> Sort) = builder().add()

    fun LIMIT(limit: Int) = OffsetAndLimit(0, limit).add()

    fun LIMIT(offset: Int, limit: Int) = OffsetAndLimit(offset, limit).add()

    fun RETURN(ret: Expression<T>): Expression<T> = Return(ret).add()

    fun RETURN(ret: IterableExpression<T>): Expression<T> = Return(ret.toIterator()).add()

    fun INSERT(what: Expression<T>) = InsertPreStage(what)
    
    fun INSERT(what: IterableExpression<T>) = InsertPreStage(what.toIterator())

    infix fun InsertPreStage<T>.INTO(collection: CollectionDefinition<T>): Expression<T> = InsertInto(what, collection).add()

    override fun printAql(p: AqlPrinter) {

        p.append("FOR ").append(name).append(" IN ").append(iterable).appendLine()

        p.indent {
            append(items)
        }
    }
}
