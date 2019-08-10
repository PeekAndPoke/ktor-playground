package de.peekandpoke.karango.aql

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.ICollection

@KarangoFuncMarker
fun <T: Entity> UPSERT(entity: T) = UpsertPreStageEntity(entity)

@KarangoFuncMarker
infix fun <T : Entity> UpsertPreStageEntity<T>.INTO(collection: ICollection<T>) = UpsertInto(entity, collection)

class UpsertPreStageEntity<T: Entity> internal constructor(val entity: T)


class UpsertInto<T : Entity>(private val entity: T, private val col: ICollection<T>) : TerminalExpr<T> {

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
