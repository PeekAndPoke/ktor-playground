package de.peekandpoke.karango.aql

@KarangoDslMarker
val <T> Expression<T>.ASC: Sort get() = SortBy(this, Direction.ASC)

@KarangoDslMarker
val <T> Expression<T>.DESC: Sort get() = SortBy(this, Direction.DESC)

@KarangoDslMarker
fun <T> Expression<T>.sort(direction: Direction): Sort = SortBy(this, direction)

interface Sort : Statement

enum class Direction(val op: String) {
    ASC("ASC"),
    DESC("DESC"),
}

internal data class SortBy(val expr: Expression<*>, val direction: Direction) : Sort {

    override fun printAql(p: AqlPrinter) =
        p.append("SORT ").append(expr).append(" ${direction.op}").appendLine()
}


