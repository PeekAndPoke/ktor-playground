package de.peekandpoke.ultra.vault.jackson

import com.fasterxml.jackson.databind.module.SimpleModule
import de.peekandpoke.ultra.vault.Ref
import de.peekandpoke.ultra.vault.Stored

class VaultJacksonModule : SimpleModule() {

    init {
        // Stored<T> deserializer
        addDeserializer(Stored::class.java, StoredDeserializer())

        // References
        addSerializer(Ref::class.java, EntityRefSerializer())
        addDeserializer(Ref::class.java, EntityRefDeserializer())
    }
}
