package de.peekandpoke.karango.query

import com.fasterxml.jackson.core.type.TypeReference
import de.peekandpoke.karango.*

data class TypedQuery<T>(val aql: String, val vars: Map<String, Any>, val type: TypeReference<T>)

fun <T> query(builder: RootBuilder.() -> Expression<T>): TypedQuery<T> {

    val root = RootBuilder()
    val returnType = root.builder()

    val query = AqlPrinter().append(root).build()

    return TypedQuery(query.query, query.vars, returnType.getType())
}

@Suppress("FunctionName")
@KarangoDslMarker
class RootBuilder internal constructor() : ForBuilderTrait, Printable {

    override val items = mutableListOf<Printable>()

    inline fun <reified T> LET(name: String, value: T) = ScalarLet(name, value, typeRef()).add().toExpression()

    inline fun <reified T, L : List<T>> LET(name: String, builder: () -> L) = IterableLet(name, builder(), typeRef()).add().toExpression()

    fun <T> RETURN(named: NamedExpression<T>) = ReturnNamed(named, named.getType()).add()

    inline fun <reified T> RETURN(vararg ids: String) = ReturnDocumentsByIds(ids.toList(), typeRef<T>()).add()

    fun <T> RETURN(type: TypeRef<T>, vararg ids: String) = ReturnDocumentsByIds(ids.toList(), type).add()

    inline fun <reified T> RETURN(collection: String, key: String) = RETURN(collection, key, typeRef<T>())

    fun <T> RETURN(collection: String, key: String, type: TypeRef<T>) = ReturnDocumentById("$collection/$key", type).add()

    fun <T : Entity, D : CollectionDefinition<T>> UPDATE(entity: T, col: D, builder: KeyValueBuilder<T>.(D) -> Unit) =
        UpdateDocument(entity, col, KeyValueBuilder<T>().apply { builder(col) }).add()

    override fun printAql(p: AqlPrinter) {
        p.append(items)
    }
}


@Suppress("FunctionName")
@KarangoDslMarker
class KeyValueBuilder<T : Entity> {

    val pairs = mutableMapOf<String, Any>()

    infix fun <X> PropertyPath<T, X>.with(value: X) =
    // TODO: this does not look right here
        apply { pairs[this.getPath().joinToString("").trimStart('.')] = value as Any }

    infix fun String.with(value: Any) = apply { pairs[this] = value }
}

interface BuilderTrait {

    val items: MutableList<Printable>

    fun <T : Printable> T.add(): T = apply { items.add(this) }
}
