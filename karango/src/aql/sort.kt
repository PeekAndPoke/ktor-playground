package de.peekandpoke.karango.aql


interface Sort : Statement {

    enum class Direction(val op: String) {
        ASC("ASC"),
        DESC("DESC"),
    }
}

val <T> Expression<T>.ASC: Sort get() = SortBy(this, Sort.Direction.ASC)
val <T> Expression<T>.DESC: Sort get() = SortBy(this, Sort.Direction.DESC)

internal data class SortBy(val expr: Expression<*>, val direction: Sort.Direction) : Sort {

    override fun printAql(p: AqlPrinter) {
        p.append("SORT ").append(expr).append(" ${direction.op}").appendLine()
    }
}


