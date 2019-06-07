package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition
import de.peekandpoke.karango.Entity

// TODO: what is the real return value of an upsert ?
class UpsertDocument<T : Entity>(private val entity: T, private val col: CollectionDefinition<T>) : TerminalExpr<T> {

    override fun innerType() = col.getType().down<T>()

    override fun getType() = col.getType()

    override fun printAql(p: AqlPrinter) {

        with(p) {
            append("UPSERT { _key: \"").append(entity._id!!.ensureKey).append("\" }").appendLine()
            indent {
                append("INSERT ").value("v", entity).appendLine()
                append("UPDATE ").value("v", entity).append(" IN ").append(col.getAlias()).appendLine()
                append("RETURN NEW").appendLine()
            }
        }
    }
}
