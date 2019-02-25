package de.peekandpoke.karango.aql

data class IteratorExpr<T>(private val __name__: String, private val __inner__: Expression<List<T>>) : Expression<T> {

    override fun getType() = __inner__.getType().down<T>()
    override fun printAql(p: AqlPrinter) = p.name(__name__)
}

@Suppress("FunctionName")
interface ForBuilderTrait : BuilderTrait {

    class For(private val trait: ForBuilderTrait, private val iteratorName: String) {

        infix fun <T> IN(forIn: In<T>): TerminalExpr<T> {

            val iterator = IteratorExpr(iteratorName, forIn.iterable)
            val loop = ForLoop(iterator, forIn.iterable)
            val result = loop.(forIn.builder)(iterator)

            trait.items.add(loop)

            return result
        }
    }

    class In<T>(internal val iterable: Expression<List<T>>, internal val builder: ForLoop<T>.(IteratorExpr<T>) -> TerminalExpr<T>)

    operator fun <T> Expression<List<T>>.invoke(builder: ForLoop<T>.(IteratorExpr<T>) -> TerminalExpr<T>) = In(this, builder)

    fun FOR(iteratorName: String) = For(this, iteratorName)
}

@Suppress("FunctionName")
@KarangoDslMarker
class ForLoop<T> internal constructor(private val name: IteratorExpr<T>, private val iterable: Expression<List<T>>) :
    ForBuilderTrait, InsertBuilderTrait, Statement {

    override val items = mutableListOf<Printable>()

    fun FILTER(predicate: Expression<Boolean>): Unit = run { Filter(predicate).add() }

    fun SORT(sort: Sort): Unit = run { sort.add() }

    fun LIMIT(limit: Int): Unit = run { OffsetAndLimit(0, limit).add() }

    fun LIMIT(offset: Int, limit: Int): Unit = run { OffsetAndLimit(offset, limit).add() }

    fun RETURN(ret: Expression<T>): TerminalExpr<T> = Return(ret).add()

    override fun printAql(p: AqlPrinter) {

        p.append("FOR ").append(name).append(" IN ").append(iterable).appendLine()

        p.indent {
            append(items)
        }
    }
}
