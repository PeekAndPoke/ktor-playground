package de.peekandpoke.karango_ktor

import de.peekandpoke.karango.*
import de.peekandpoke.karango.aql.EQ
import de.peekandpoke.karango.aql.FOR
import io.ktor.features.DataConversion
import io.ktor.features.NotFoundException
import io.ktor.util.KtorExperimentalAPI
import kotlin.reflect.KClass


@KtorExperimentalAPI
fun DataConversion.Configuration.add(db: Db) {

    db.getEntityCollections().forEach {

        val kClass = it.coll.getType().down<Any>().toClass().kotlin

        convert(kClass) {

            encode { value ->
                if (value == null) emptyList() else listOf((value as Entity)._key!!)
            }

            decode { values, _ ->
                it.findByKey(values.first()) ?: throw NotFoundException("No '${kClass.java.canonicalName}' found for $values")
            }
        }
    }
}

@KtorExperimentalAPI
inline fun <reified T: Entity> DataConversion.Configuration.add(noinline provider: () -> DbEntityCollection<T>) {

    convert(T::class) {

        encode { if (it == null) emptyList() else listOf((it as T)._key!!) }

        decode { values, _ ->

            provider().findByKey(values.first())
                ?: throw NotFoundException("No '${T::class.java.canonicalName}' found for $values")
        }
    }
}

@Deprecated("Use the version above instead")
@KtorExperimentalAPI
inline fun <reified X: Entity, reified T : X> DataConversion.Configuration.add(
    db: Db,
    collection: ICollection<X>,
    type: KClass<T>
) {

    convert(type) {
        encode { if (it == null) emptyList() else listOf((it as T)._key!!) }

        decode { values, _ ->

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
