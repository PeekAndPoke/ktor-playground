package de.peekandpoke.ultra.vault.slumber

import de.peekandpoke.ultra.slumber.Awaker
import de.peekandpoke.ultra.slumber.Slumberer
import de.peekandpoke.ultra.vault.NullEntityCache
import de.peekandpoke.ultra.vault.Ref

object RefCodec : Awaker, Slumberer {

    override fun awake(data: Any?, context: Awaker.Context): Any? {

        if (data !is String) {
            return null
        }

        return context.attributes[VaultSlumberModule.DatabaseKey]?.let { database ->

            val cache = context.attributes[VaultSlumberModule.EntityCacheKey] ?: NullEntityCache

            val coll = data.split("/").first()

            cache.getOrPut(data) { database.getRepository(coll)?.findById(data) }?.asRef
        }
    }

    override fun slumber(data: Any?, context: Slumberer.Context): Any? = when (data) {

        is Ref<*> -> data._id

        else -> null
    }
}
