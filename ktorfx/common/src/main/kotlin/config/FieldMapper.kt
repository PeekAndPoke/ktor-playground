package de.peekandpoke.ktorfx.common.config

import de.peekandpoke.ktorfx.common.isPrimitiveOrString
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.primaryConstructor

@KtorExperimentalAPI
interface FieldMapper<T> {

    fun canHandle(type: KType): Boolean

    fun get(config: ApplicationConfig, type: KType, field: String, path: String): T?

    fun map(value: String): T?

    class FallbackMapper : FieldMapper<Nothing?> {
        override fun canHandle(type: KType) = true

        override fun get(config: ApplicationConfig, type: KType, field: String, path: String): Nothing? = null

        override fun map(value: String): Nothing? = null
    }

    abstract class BaseMapper<T : Any>(private val cls: KClass<T>) : FieldMapper<T> {

        override fun canHandle(type: KType) = type.classifier == cls

        override fun get(config: ApplicationConfig, type: KType, field: String, path: String): T? {
            return config.string(field)?.let { map(it) }
        }

        fun ApplicationConfig.string(field: String) = propertyOrNull(field)?.getString()
    }

    class BooleanMapper : BaseMapper<Boolean>(Boolean::class) {
        override fun map(value: String) = value.toBoolean()
    }

    class CharMapper : BaseMapper<Char>(Char::class) {
        override fun map(value: String) = value.firstOrNull()
    }

    class DoubleMapper : BaseMapper<Double>(Double::class) {
        override fun map(value: String) = value.toDoubleOrNull()
    }

    class FloatMapper : BaseMapper<Float>(Float::class) {
        override fun map(value: String) = value.toFloatOrNull()
    }

    class IntMapper : BaseMapper<Int>(Int::class) {
        override fun map(value: String) = value.toIntOrNull()
    }

    class LongMapper : BaseMapper<Long>(Long::class) {
        override fun map(value: String) = value.toLongOrNull()
    }

    class StringMapper : BaseMapper<String>(String::class) {
        override fun map(value: String) = value
    }

    class PrimitiveListMapper(private val mapper: ConfigMapper) : FieldMapper<List<*>> {

        override fun canHandle(type: KType): Boolean {
            val cls = type.classifier as KClass<*>

            return cls == List::class &&
                    type.arguments.size == 1 &&
                    type.arguments[0].type!!.isPrimitiveOrString
        }

        override fun get(config: ApplicationConfig, type: KType, field: String, path: String): List<*>? {

            val data = config.property(field).getList()

            val childType = type.arguments[0].type!!
            val childMapper = mapper.getFieldMapper(childType)

            return data.map { childMapper.map(it) }
        }

        override fun map(value: String): Nothing? = null
    }

    class DataClassMapper(private val mapper: ConfigMapper) : FieldMapper<Any> {

        override fun canHandle(type: KType) = (type.classifier as KClass<*>).isData

        override fun get(config: ApplicationConfig, type: KType, field: String, path: String): Any? {

            val data = when {
                field.isEmpty() -> config
                else -> try {
                    config.config(field)
                } catch (e: Throwable) {
                    return null
                }
            }

            val cls = type.classifier as KClass<*>
            val ctor = cls.primaryConstructor!!

            val params = ctor.parameters.mapNotNull { param ->

                val mapper = mapper.getFieldMapper(param.type)
                val name = param.name!!
                val newPath = when {
                    path.isEmpty() -> name
                    else -> "$path.$name"
                }

                val value = mapper.get(data, param.type, name, newPath)

                return@mapNotNull when {
                    // null and nullable
                    value == null && param.type.isMarkedNullable -> param to null
                    // null and optional
                    value == null && param.isOptional -> null
                    // invalid null
                    value == null -> throw ConfigError("Field '$newPath' is missing or invalid")
                    // default
                    else -> param to value
                }
            }.toMap()

            return ctor.callBy(params)
        }

        override fun map(value: String): Nothing? = null
    }
}
