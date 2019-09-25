package io.ultra.ktor_tools.typedroutes

import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.Stored
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class IncomingVaultConverter(private val db: Database) : IncomingParamConverter {

    override fun canHandle(type: Type): Boolean {
        return type is ParameterizedType && type.rawType == Stored::class.java
    }

    override fun convert(value: String, type: Type): Stored<Any> {

        val innerCls = (type as ParameterizedType).actualTypeArguments[0] as Class<*>

        @Suppress("UNCHECKED_CAST")
        return db.getRepositoryStoring(innerCls).findById(value) as Stored<Any>
    }
}

class OutgoingVaultConverter : OutgoingParamConverter {

    override fun canHandle(type: Type): Boolean {
        return type is ParameterizedType && Storable::class.java.isAssignableFrom((type.rawType as Class<*>))
    }

    override fun convert(value: Any, type: Type): String {
        return (value as Storable<*>)._key
    }
}
