package de.peekandpoke.karango_ktor

import de.peekandpoke.karango.*
import de.peekandpoke.karango.aql.EQ
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.aql.RETURN
import io.ktor.features.DataConversion
import io.ktor.features.NotFoundException
import io.ktor.util.ConversionService
import io.ktor.util.KtorExperimentalAPI
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

// TODO: we need to get rid of this mess
//   Method 1: Let TypeRef only produce a tree with Class entries instead of ParameterizedTypeImpl
//   Method 2: Pull request to ktor

internal class BetterMap<Type, ConversionService>(private val wrapped: MutableMap<Type, ConversionService>) : MutableMap<Type, ConversionService> by wrapped {

    private val stringLookUp = mutableMapOf<String, ConversionService?>()

    override operator fun get(key: Type): ConversionService? {

        val found = wrapped[key]

        if (found != null) {
            return found
        }

        if (key is ParameterizedType) {
            return stringLookUp.getOrPut(key.toString()) {
                wrapped.entries.filter { (k, _) -> k.toString() == key.toString() }
                    .map { (_, v) -> v }
                    .firstOrNull()
            }
        }

        return null
    }
}

@KtorExperimentalAPI
fun DataConversion.Configuration.add(db: Db) {

    // TODO: we need unit-tests that will fail as soon as the inner impl has changed

    // Access internal field via reflection
    val convertersField = this::class.java.declaredFields.first { it.name == "converters" }.apply { isAccessible = true }
    // read, wrap the "converters"
    @Suppress("UNCHECKED_CAST")
    val converters: MutableMap<Type, ConversionService> = BetterMap(convertersField.get(this) as MutableMap<Type, ConversionService>)
    // write the wrapped "converters" back
    convertersField.set(this, converters)

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // codec for each database table

    db.getEntityCollections().forEach {

        val kStoredClass = it.coll.getType().down<Any>().wrapWith<Stored<*>>(Stored::class.java).type

        println(kStoredClass)

        converters[kStoredClass] = object : ConversionService {
            override fun fromValues(values: List<String>, type: Type): Any? {
                return it.findByKey(values.first()) ?: throw NotFoundException("No '${type}' found for $values")
            }

            override fun toValues(value: Any?): List<String> {
                return if (value == null) emptyList() else listOf((value as Stored<*>)._key)
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Special encoder for all [Stored] entities

    convert(Stored::class) {
        encode { value ->
            if (value == null) emptyList() else listOf((value as Stored<*>)._key)
        }
    }
}

@KtorExperimentalAPI
inline fun <reified T : Entity> DataConversion.Configuration.add(noinline provider: () -> DbEntityCollection<T>) {

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
inline fun <reified X : Entity, reified T : X> DataConversion.Configuration.add(
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
