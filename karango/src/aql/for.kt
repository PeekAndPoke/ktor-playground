package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition

@Suppress("FunctionName")
interface ForBuilderTrait : BuilderTrait {

    class For(private val trait: ForBuilderTrait, private val iteratorName: String) {
        
        infix fun <T> IN(forIn: In<T>) : Expression<T> {

            val iterator = IteratorExpr(iteratorName, forIn.iterable)
            val loop = ForLoopBuilder(iterator, forIn.iterable)
            val result = loop.(forIn.builder)(iterator)

            trait.items.add(loop)

            return result
        }
    }
    
    operator fun <T> IterableExpression<T>.invoke(builder: ForLoopBuilder<T>.(IteratorExpr<T>) -> Expression<T>) = In(this, builder)
    
    class In<T>(internal val iterable: IterableExpression<T>, internal val builder: ForLoopBuilder<T>.(IteratorExpr<T>) -> Expression<T>)
    
    fun FOR(iteratorName: String) = For(this, iteratorName) 
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

    fun INSERT(what: Expression<T>) = InsertPreStage(what)

    infix fun InsertPreStage<T>.INTO(collection: CollectionDefinition<T>): Expression<T> = InsertInto(what, collection).add()

    override fun printAql(p: AqlPrinter) {

        p.append("FOR ").append(name).append(" IN ").append(iterable).appendLine()

        p.indent {
            append(items)
        }
    }
}
