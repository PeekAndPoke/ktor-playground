@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.ICollection
import de.peekandpoke.ultra.vault.type


// TODO: UPDATE
@Suppress("unused")
@KarangoTerminalFuncMarker
fun <T : Entity, D : ICollection<T>> StatementBuilder.UPDATE(entity: T, col: D, builder: KeyValueBuilder<T>.(Expression<T>) -> Unit): TerminalExpr<Any> =
    UpdateDocument(
        entity,
        col,
        KeyValueBuilder<T>().apply { builder(ExpressionImpl(col.getAlias(), col.getType().down())) }
    )

@Suppress("FunctionName")
@KarangoDslMarker
class KeyValueBuilder<T : Entity> {

    // TODO: is this one still needed? For Update queries?

//    val pairs = mutableListOf<Pair<PropertyPath<*>, Any>>()
//
//    infix fun <X> PropertyPath<X>.with(value: X) = apply { pairs.add(Pair(this, value as Any)) }
}


class UpdateDocument<T : Entity>(
    private val entity: T,
    private val col: ICollection<T>,
    private val kv: KeyValueBuilder<T>
) : TerminalExpr<Any> { // TODO: what is the real return value of an update ?

    override fun innerType() = type<Any>()

    override fun getType() = type<List<Any>>()

    override fun printAql(p: AqlPrinter) {

        with(p) {
            append("UPDATE \"").append(entity._id!!.ensureKey).append("\" WITH {").appendLine()
            indent {
//                val pairs = kv.pairs.toMap()
//                pairs.keys.forEachIndexed { idx, key ->
//                    // TODO: FIX the update
////                    append(key.allExpressions().drop(1)).append(" : ").value("kv", pairs.getValue(key))
////
////                    if (idx < pairs.size - 1) {
////                        append(",")
////                    }
//                    appendLine()
//                }
            }

            append("} IN ").name(col.getAlias()).appendLine()
        }
    }
}
