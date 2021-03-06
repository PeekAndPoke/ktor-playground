@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.ICollection
import de.peekandpoke.ultra.common.TypeRef
import de.peekandpoke.ultra.common.unList
import de.peekandpoke.ultra.vault.Storable

@Suppress("unused")
@KarangoTerminalFuncMarker
fun <T> StatementBuilder.UPSERT(entity: Storable<T>) = UpsertPartial(entity)

class UpsertPartial<T> internal constructor(val entity: Storable<T>)

@KarangoTerminalFuncMarker
infix fun <T> UpsertPartial<T>.INTO(collection: ICollection<T>): TerminalExpr<T> = UpsertInto(entity, collection)

internal class UpsertInto<T>(private val entity: Storable<T>, private val coll: ICollection<T>) : TerminalExpr<T> {

    override fun innerType(): TypeRef<T> = coll.getType().unList

    override fun getType() = coll.getType()

    override fun printAql(p: AqlPrinter) {

        with(p) {
            append("UPSERT { _key: \"").append(entity._key).append("\" }").appendLine()
            indent {
                append("INSERT ").value("v", entity).appendLine()
                append("UPDATE ").value("v", entity).append(" IN ").append(coll.getAlias()).appendLine()
                append("RETURN NEW").appendLine()
            }
        }
    }
}
