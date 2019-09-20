@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.ICollection
import de.peekandpoke.ultra.vault.Storable

@Suppress("unused")
@KarangoTerminalFuncMarker
fun <T> StatementBuilder.UPSERT(entity: Storable<T>) = UpsertPartial(entity)

class UpsertPartial<T> internal constructor(val entity: Storable<T>)

@KarangoTerminalFuncMarker
infix fun <T> UpsertPartial<T>.INTO(collection: ICollection<T>): TerminalExpr<T> = UpsertInto(entity, collection)

internal class UpsertInto<T>(private val entity: Storable<T>, private val col: ICollection<T>) : TerminalExpr<T> {

    override fun innerType() = col.getType().down<T>()

    override fun getType() = col.getType()

    override fun printAql(p: AqlPrinter) {

        with(p) {
            append("UPSERT { _key: \"").append(entity._key).append("\" }").appendLine()
            indent {
                append("INSERT ").value("v", entity.value).appendLine()
                append("UPDATE ").value("v", entity.value).append(" IN ").append(col.getAlias()).appendLine()
                append("RETURN NEW").appendLine()
            }
        }
    }
}
