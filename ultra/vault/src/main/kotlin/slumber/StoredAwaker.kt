package de.peekandpoke.ultra.vault.slumber

import de.peekandpoke.ultra.slumber.Awaker
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.StorableMeta
import de.peekandpoke.ultra.vault.Stored
import kotlin.reflect.KType
import kotlin.reflect.full.createType

class StoredAwaker(private val innerType: KType) : Awaker {

    companion object {
        private val StorableMetaType = StorableMeta::class.createType(nullable = true)
    }

    override fun awake(data: Any?, context: Awaker.Context): Any? {

        if (data !is Map<*, *>) {
            return null
        }

        val id = data["_id"]
        val key = data["_key"]
        val rev = data["_rev"]

        return when {
            id is String && key is String && rev is String -> {

                val value = context.awake(innerType, data)

                val meta = context.awake(StorableMetaType, data[Storable<*>::_meta.name]) as StorableMeta?

                return Stored(value = value, _id = id, _key = key, _rev = rev, _meta = meta)
            }

            else -> null
        }
    }
}
