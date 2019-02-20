@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition

inline fun <reified T> DOCUMENT(id: String): Expression<T> =
    FuncCall(
        typeRef(),
        AqlFunc.DOCUMENT,
        listOf(Value("id", id))
    )

inline fun <reified T> DOCUMENT(vararg ids: String): IterableExpression<T> = DOCUMENT(ids.toList())

inline fun <reified T> DOCUMENT(ids: List<String>): IterableExpression<T> =
    IterableFuncCall(
        "doc",
        typeRef(),
        AqlFunc.DOCUMENT,
        listOf(
            ArrayValue("ids", ids)
        )
    )

fun <T> DOCUMENT(collection: CollectionDefinition<T>, key: String): Expression<T> =
    FuncCall(
        collection.getType(),
        AqlFunc.DOCUMENT,
        listOf(Value("id", "${collection.getAlias()}/${key.ensureKey}"))
    )

fun <T> DOCUMENT(collection: CollectionDefinition<T>, vararg keys: String) = DOCUMENT(collection, keys.toList())

fun <T> DOCUMENT(collection: CollectionDefinition<T>, keys: List<String>) = DOCUMENT(collection.getType(), collection.getAlias(), keys)

fun <T> DOCUMENT(type: TypeRef<T>, collection: String, keys: List<String>): IterableExpression<T> =
    IterableFuncCall(
        "doc",
        type,
        AqlFunc.DOCUMENT,
        listOf(
            ArrayValue("ids", keys.map { "$collection/${it.ensureKey}" })
        )
    )
