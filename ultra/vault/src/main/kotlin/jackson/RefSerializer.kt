package de.peekandpoke.ultra.vault.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import de.peekandpoke.ultra.vault.Ref

internal class RefSerializer : StdSerializer<Ref<*>>(Ref::class.java) {

    override fun serialize(value: Ref<*>?, gen: JsonGenerator, provider: SerializerProvider) {

        if (value == null) {
            gen.writeNull()
        } else {
            gen.writeString(value._id)
        }
    }
}
