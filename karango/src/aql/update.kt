package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition
import de.peekandpoke.karango.Entity

class UpdateDocument<T : Entity>(
    private val entity: T, 
    private val col: CollectionDefinition<T>, 
    private val kv: KeyValueBuilder<T>
) : TerminalExpr<Any> { // TODO: what is the real return value of an update ?

    override fun innerType() = type<Any>()

    override fun getType() = type<List<Any>>()

    override fun printAql(p: AqlPrinter) {

        with(p) {
            append("UPDATE \"").append(entity._id.ensureKey).append("\" WITH {").appendLine()
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
