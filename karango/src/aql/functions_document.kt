@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.ICollection
import de.peekandpoke.ultra.vault.TypeRef
import de.peekandpoke.ultra.vault.kType
import de.peekandpoke.ultra.vault.unList

/**
 * Get a single document by its full id.
 */
inline fun <reified T> DOCUMENT(id: String): Expression<T> =
    AqlFunc.DOCUMENT.call(kType(), id.aql("id"))

/**
 * Get a single document by its full id and deserialize it to the given cls
 */
fun <T : Any> DOCUMENT(cls: Class<T>, id: String): Expression<T> =
    AqlFunc.DOCUMENT.call(cls.kType(), id.aql("id"))

/**
 * Get a single document from the given collection by its key.
 */
fun <T> DOCUMENT(collection: ICollection<T>, key: String): Expression<T> =
    AqlFunc.DOCUMENT.call(
        collection.getType().unList,
        "${collection.getAlias()}/${key.ensureKey}".aql("id")
    )

/**
 * Get a list of documents by their IDs
 */
inline fun <reified T> DOCUMENT(vararg ids: String): Expression<List<T>> =
    DOCUMENT(ids.toList())

/**
 * Get a list of documents by their IDs
 */
inline fun <reified T> DOCUMENT(ids: List<String>): Expression<List<T>> =
    AqlFunc.DOCUMENT.call(kType(), ids.aql("ids"))

/**
 * Get a list of documents from the given collection by their keys.
 */
fun <T> DOCUMENT(collection: ICollection<T>, vararg keys: String) =
    DOCUMENT(collection, keys.toList())

/**
 * Get a list of documents from the given collection by their keys.
 */
fun <T> DOCUMENT(collection: ICollection<T>, keys: List<String>) =
    DOCUMENT(collection.getType(), collection.getAlias(), keys)

/**
 * Get a list of documents of the given type from the given collection by their keys.
 */
fun <T> DOCUMENT(type: TypeRef<List<T>>, collection: String, keys: List<String>): Expression<List<T>> =
    AqlFunc.DOCUMENT.call(type, keys.map { "$collection/${it.ensureKey}" }.aql("ids"))
