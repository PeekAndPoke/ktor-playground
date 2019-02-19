@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition

inline fun <reified T> DOCUMENT(id: String): Expression<T> =
    FuncCall(
        typeRef(),
        AqlFunc.DOCUMENT,
        listOf(Value("id", id))
    )

inline fun <reified T> DOCUMENT(vararg ids: String): Expression<T> = DOCUMENT(ids.toList())

inline fun <reified T> DOCUMENT(ids: List<String>): IterableExpression<T> =
    IterableFuncCall(
        "doc",
        typeRef(),
        AqlFunc.DOCUMENT,
        ids.map { Value("id", it) }
    )

fun <T> DOCUMENT(collection: CollectionDefinition<T>, key: String): Expression<T> =
    FuncCall(
        collection.getType(),
        AqlFunc.DOCUMENT,
        listOf(Value("id", "${collection.getName()}/${key.ensureKey}"))
    )

fun <T> DOCUMENT(collection: CollectionDefinition<T>, vararg keys: String) = DOCUMENT(collection, keys.toList())

fun <T> DOCUMENT(collection: CollectionDefinition<T>, keys: List<String>): IterableExpression<T> =
    IterableFuncCall(
        "doc",
        collection.getType(),
        AqlFunc.DOCUMENT,
        // TODO: fix me ... we need one parameter (ArrayValue)
        keys.map { Value("id", "${collection.getName()}/${it.ensureKey}") }
    )
