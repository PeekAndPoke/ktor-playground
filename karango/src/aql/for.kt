package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition

@Suppress("FunctionName")
interface ForBuilderTrait : BuilderTrait {

    fun <T, D : NamedIterableExpression<T>> FOR(col: D, builder: ForLoopBuilder<T>.(D) -> Expression<T>): Expression<T> {

        val forLoop = ForLoopBuilder(col)
        val returnType = forLoop.builder(col)

        items.add(forLoop)

        return returnType
    }
}

@Suppress("FunctionName")
@KarangoDslMarker
class ForLoopBuilder<T> internal constructor(private val iterable: NamedIterableExpression<T>) : ForBuilderTrait, Expression<T> {

    override val items = mutableListOf<Printable>()

    override fun getType() = iterable.getType()

    fun FILTER(builder: () -> Expression<Boolean>) = apply { items.add(Filter(builder())) }

    fun SORT(builder: () -> Sort) = builder().add()

    fun LIMIT(limit: Int) = OffsetAndLimit(0, limit).add()

    fun LIMIT(offset: Int, limit: Int) = OffsetAndLimit(offset, limit).add()

    fun RETURN(ret: NamedExpression<T>): Expression<T> = ReturnNamed(ret, ret.getType()).add()

    fun RETURN(ret: NamedIterableExpression<T>): Expression<T> = ReturnIterator(ret, ret.getType()).add()

    fun INSERT(what: NamedExpression<T>) = InsertPreStage(what)

    infix fun InsertPreStage<T>.INTO(collection: CollectionDefinition<T>): Expression<T> = InsertInto(what, collection).add()

    override fun printAql(p: AqlPrinter) {

        p.append("FOR ").iterator(iterable).append(" IN ").name(iterable).appendLine()

        p.indent {
            append(items)
        }
    }
}
