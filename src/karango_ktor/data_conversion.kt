package de.peekandpoke.karango_ktor

import de.peekandpoke.karango.aql.replaceHiddenFieldWith
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.Stored
import de.peekandpoke.ultra.vault.TypeRef
import io.ktor.features.DataConversion
import io.ktor.features.NotFoundException
import io.ktor.util.ConversionService
import io.ktor.util.KtorExperimentalAPI
import org.slf4j.Logger
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

// TODO: move this class to ktor_tools

// TODO: we need to get rid of this mess
//   Method 1: Let TypeRef only produce a tree with Class entries instead of ParameterizedTypeImpl
//   Method 2: Pull request to ktor

/**
 * A better lookup that handles inconsistencies of [ParameterizedType]
 *
 * Background:
 *
 * A ParameterizedType coming from [TypeRef] might look different then a one constructed through the compiler.
 *
 * Example:
 *
 * The compiler might create a ParameterizedType looking like "OuterClass<class InnerClass>".
 * While [TypeRef] might produce something looking like "OuterClass<InnerClass>"
 */
internal class BetterMap(private val wrapped: MutableMap<Type, ConversionService>) : MutableMap<Type, ConversionService> by wrapped {

    private val parameterizedTypeLookUp = mutableMapOf<ParameterizedType, ConversionService?>()

    override operator fun get(key: Type): ConversionService? {

        val found = wrapped[key]

        if (found != null) {
            return found
        }

        if (key is ParameterizedType) {

            return parameterizedTypeLookUp.getOrPut(key) {
                wrapped.entries
                    .filter { (k, _) -> k.toString() == key.toString() }
                    .map { (_, v) -> v}
                    .firstOrNull()
            }
        }

        return null
    }
}

@KtorExperimentalAPI
fun DataConversion.Configuration.add(db: Database, logger: Logger) {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // replace the internal "converters" map with our implementation

    val converters = replaceHiddenFieldWith("converters") { it: MutableMap<Type, ConversionService> -> BetterMap(it)}

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // codec for each database table

    db.getRepositories().forEach {

        val storedType = it.storedType.wrapWith(Stored::class.java).type

        logger.info("Setting up converter for type '$storedType'")

        converters[storedType] = object : ConversionService {
            override fun fromValues(values: List<String>, type: Type): Any? {
                return it.findById(values.first()) ?: throw NotFoundException("No '${type}' found for $values")
            }

            override fun toValues(value: Any?): List<String> {
                return if (value == null) emptyList() else listOf((value as Stored<*>)._key)
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Special encoder for all [Stored] entities (needed when encoding URLs)

    convert(Stored::class) {
        encode { value ->
            if (value == null) emptyList() else listOf((value as Stored<*>)._key)
        }
    }
}
