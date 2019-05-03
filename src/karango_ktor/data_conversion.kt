package de.peekandpoke.karango_ktor

import de.peekandpoke.karango.*
import de.peekandpoke.karango.aql.EQ
import de.peekandpoke.karango.aql.FOR
import io.ktor.features.DataConversion
import io.ktor.features.NotFoundException
import io.ktor.util.KtorExperimentalAPI
import kotlin.reflect.KClass

@KtorExperimentalAPI
inline fun <reified X, reified T : X> DataConversion.Configuration.add(
    db: Db,
    collection: CollectionDefinition<X>,
    type: KClass<T>
) where X : Entity, X : WithKey {

    convert(type) {
        encode { if (it == null) emptyList() else listOf((it as T)._key!!) }

        decode { values, type ->

            val result = db.query {
                FOR(collection) { c ->
                    FILTER(c._key EQ values.first())
                    RETURN(c)
                }
            }

            result.toList().firstOrNull() ?: throw NotFoundException("No '${T::class.java.canonicalName}' found for $values")
        }
    }
}
