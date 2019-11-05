package de.peekandpoke.ultra.vault.slumber

import de.peekandpoke.ultra.common.TypedKey
import de.peekandpoke.ultra.slumber.Awaker
import de.peekandpoke.ultra.slumber.SlumberModule
import de.peekandpoke.ultra.slumber.Slumberer
import de.peekandpoke.ultra.vault.*
import kotlin.reflect.KType

object VaultSlumberModule : SlumberModule {

    val DatabaseKey = TypedKey<Database>("vault_Database")
    val EntityCacheKey = TypedKey<EntityCache>("vault_EntityCache")

    override fun getAwaker(type: KType): Awaker? {
        return when (type.classifier) {
            Ref::class -> RefCodec
            Stored::class -> StoredAwaker(type.arguments[0].type!!)
            else -> null
        }
    }

    override fun getSlumberer(type: KType): Slumberer? {
        return when (type.classifier) {
            Ref::class -> RefCodec
            in listOf(Stored::class, New::class) -> StoredSlumberer
            else -> null
        }
    }
}
