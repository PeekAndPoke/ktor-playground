package de.peekandpoke.ktorfx.common.config

import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType

@KtorExperimentalAPI
class ConfigMapper {

    private val mappers = listOf(
        // Primitives and Strings
        FieldMapper.BooleanMapper(),
        FieldMapper.CharMapper(),
        FieldMapper.DoubleMapper(),
        FieldMapper.FloatMapper(),
        FieldMapper.IntMapper(),
        FieldMapper.LongMapper(),
        FieldMapper.StringMapper(),
        // Collections
        FieldMapper.PrimitiveListMapper(this),
        // Data classes
        FieldMapper.DataClassMapper(this),
        // Fallback
        FieldMapper.FallbackMapper()
    )

    fun <T : AppConfig> map(config: ApplicationConfig, target: KClass<T>): T {

        val type = target.createType(
            target.typeParameters.map { KTypeProjection.STAR }
        )

        @Suppress("UNCHECKED_CAST")
        return getFieldMapper(type).get(config, type, "", "") as T
    }

    fun getFieldMapper(type: KType): FieldMapper<*> {
        return mappers.first { mapper -> mapper.canHandle(type) }
    }
}
