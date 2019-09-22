package de.peekandpoke

import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

/**
 * ParameterProviders provide parameters needed to instantiate services.
 */
interface ParameterProvider {

    /**
     * Provides the parameter
     */
    fun provide(container: Container): Any

    /**
     * Validates that a parameter can be provided
     *
     * When all is well an empty list is returned.
     * Otherwise a list of error strings is returned.
     */
    fun validate(container: Container): List<String>

    data class ForConfigValue(private val parameter: KParameter) : ParameterProvider {

        private val paramCls by lazy { parameter.type.classifier as KClass<*> }

        private val paramName by lazy { parameter.name!! }

        override fun provide(container: Container) = container.getConfig<Any>(paramName)

        override fun validate(container: Container) = when {

            container.hasConfig(paramName, paramCls) -> listOf()

            else -> listOf("Parameter '${paramName}' misses a config value '${paramName}' of type ${paramCls.qualifiedName}")
        }
    }

    /**
     * Parameter provider for a single object
     */
    data class ForSimpleObject(private val parameter: KParameter) : ParameterProvider {

        private val paramCls by lazy { parameter.type.classifier as KClass<*> }

        override fun provide(container: Container) = container.get(paramCls)

        override fun validate(container: Container) = when {

            container.has(paramCls) -> listOf()

            else -> listOf("Parameter '${parameter.name}' misses a dependency to '${paramCls.qualifiedName}'")
        }
    }

    /**
     * Fallback that always produces an error
     */
    data class UnknownInjection(private val parameter: KParameter) : ParameterProvider {

        private val paramCls = parameter.type.classifier as KClass<*>

        private val error = "Parameter '${parameter.name}' has no known way to inject a '${paramCls.qualifiedName}'"

        override fun provide(container: Container) = error(error)

        override fun validate(container: Container) = listOf(error)
    }

    companion object {
        fun of(parameter: KParameter): ParameterProvider {

            val paramCls = parameter.type.classifier as KClass<*>

            return when {

                paramCls.java.isPrimitive -> ForConfigValue(parameter)

                paramCls.typeParameters.isEmpty() -> ForSimpleObject(parameter)

                else -> UnknownInjection(parameter)
            }
        }
    }
}

/**
 * Base for all service providers
 */
interface ServiceProvider {
    /**
     * Provides the service instance
     */
    fun provide(container: Container): Any

    /**
     * Validates that a service can be provided.
     *
     * When all is well an empty list is returned.
     * Otherwise a list of error strings is returned.
     */
    fun validate(container: Container): List<String>
}

/**
 * Provides an already existing object as a service
 */
data class ConstantServiceProvider(private val instance: Any) : ServiceProvider {
    /**
     * Simply returns the [instance]
     */
    override fun provide(container: Container) = instance

    /**
     * Always valid
     */
    override fun validate(container: Container) = listOf<String>()
}

/**
 * Provides a singleton service
 *
 * The [owner] is the type containing the creator function [creator]
 *
 * The [paramProviders] create the parameters passed to [creator]
 */
data class SingletonServiceProvider constructor(
    val owner: KClass<*>,
    val creator: KFunction<Any>,
    val paramProviders: List<ParameterProvider>
) : ServiceProvider {

    companion object {
        fun ofConstructor(cls: KClass<*>) = SingletonServiceProvider(
            cls,
            cls.primaryConstructor!!,
            cls.primaryConstructor!!.parameters.map { ParameterProvider.of(it) }
        )
    }

    private var instance: Any? = null

    /**
     * Get or create the instance of the service
     */
    override fun provide(container: Container): Any = instance ?: create(container).apply { instance = this }

    /**
     * Validates that all parameters can be provided
     */
    override fun validate(container: Container) = paramProviders.flatMap {
        it.validate(container)
    }

    /**
     * Creates a new instance
     */
    private fun create(container: Container): Any = creator.call(
        *paramProviders.map { it.provide(container) }.toTypedArray()
    )
}

enum class InjectionType {
    Singleton,
    Dynamic
}

class ContainerBuilder(builder: ContainerBuilder.() -> Unit = {}) {

    private val config = mutableMapOf<String, Any>()

    internal val classes = mutableMapOf<KClass<*>, InjectionType>()

    init {
        builder(this)
    }

    fun build(): ContainerBlueprint = ContainerBlueprint(config.toMap(), classes)

    // adding services ///////////////////////////////////////////////////////////////////////////////////////

    fun <T : Any> add(def: KClass<T>, type: InjectionType) = apply { classes[def] = type }

    inline fun <reified T : Any> singleton() = add(T::class, InjectionType.Singleton)

    inline fun <reified T : Any> dynamic() = add(T::class, InjectionType.Dynamic)

    // adding config values //////////////////////////////////////////////////////////////////////////////////

    fun config(id: String, value: Int) = apply { config[id] = value }

    fun config(id: String, value: Long) = apply { config[id] = value }

    fun config(id: String, value: String) = apply { config[id] = value }

    fun config(id: String, value: Boolean) = apply { config[id] = value }
}

/**
 * A lookup for finding out which classes are injected into which
 */
class DependencyLookUp(classes: Set<KClass<*>>) {

    /**
     * The lookup map built by the init method
     */
    private val dependencies: Map<KClass<*>, Set<KClass<*>>>

    init {
        /**
         * Temporary map for building the lookup
         */
        val tmp = mutableMapOf<KClass<*>, MutableSet<KClass<*>>>()

        /**
         * Records the injected types of the given cls
         */
        val record = { cls: KClass<*> ->
            if (cls.primaryConstructor != null) {
                cls.primaryConstructor!!.parameters.forEach {
                    tmp.getOrPut(it.type.classifier as KClass<*>) { mutableSetOf() }.add(cls)
                }
            }
        }

        classes.forEach { record(it) }

        dependencies = tmp
    }

    fun getAllDependents(of: Set<KClass<*>>): Set<KClass<*>> {

        var current = of
        var previous = setOf<KClass<*>>()

        // Not the best/fastest implementation ...
        while (current.size > previous.size) {

            previous = current

            val found = current.flatMap { dependencies[it] ?: setOf() }

            current = current.plus(found)
        }

        return current.minus(of)
    }
}

data class BaseTypeLookup(val classes: Set<KClass<*>>) {


}

data class MandatoryDynamicsChecker(val mandatory: Set<KClass<*>>) {

    private val lookUp = mutableMapOf<Set<KClass<*>>, List<KClass<*>>>()

    /**
     * Checks if [given] contains all [mandatory]
     *
     * If all is well an empty list is returned.
     * Otherwise the list will contain all classes that are missing.
     */
    fun validate(given: Set<KClass<*>>): List<KClass<*>> {

        // A set of classes will have the same hash for the same classes, so we can do some caching here
        return lookUp.getOrPut(given) {

            mandatory.filter { mandatoryClass ->
                given.none { givenClass ->
                    mandatoryClass.java.isAssignableFrom(givenClass.java)
                }
            }
        }
    }
}

data class ContainerBlueprint(val config: Map<String, Any>, val classes: Map<KClass<*>, InjectionType>) {

    /**
     * Counts how often times the blueprint was used
     */
    private var usages = 0

    /**
     * A set of all classes that need to passed to [useWith]
     */
    private val mandatoryDynamics = classes.filterValues { it == InjectionType.Dynamic }.keys

    /**
     * Used to check whether all mandatory services are passed to [useWith]
     */
    private val mandatoryDynamicsChecker = MandatoryDynamicsChecker(mandatoryDynamics)

    /**
     * Dependency look up
     */
    private val dependencyLookUp = DependencyLookUp(classes.keys)

    /**
     * Semi dynamic services
     *
     * These are services that where initially defined as singletons, but which inject dynamic services.
     * Or which inject services that themselves inject dynamic services etc...
     */
    private val semiDynamicServices: Set<KClass<*>> = dependencyLookUp.getAllDependents(mandatoryDynamics)

    /**
     * Global services are the services that have no dependency to any of the dynamic services
     */
    private val globalServices: Map<KClass<*>, ServiceProvider> = classes
        .filterKeys { !mandatoryDynamics.contains(it) }
        .filterKeys { !semiDynamicServices.contains(it) }
        .mapValues { (k, _) -> SingletonServiceProvider.ofConstructor(k) }

    // TODO: validate the container
    //       1. check if all services are present
    //       2. check for cyclic dependencies


    fun useWith(vararg dynamics: Any): Container {

        // On the first usage we validate the consistency of the container
        if (usages++ == 0) {
            validate()
        }

        mandatoryDynamicsChecker.validate(dynamics.map { it::class }.toSet()).apply {
            if (isNotEmpty()) {
                error("Cannot create Kontainer! Some mandatory dynamic services are missing: ${map { it.qualifiedName }.joinToString(", ")}")
            }
        }

        return instantiate(
            dynamics.map { it::class to ConstantServiceProvider(it) }
        )
    }

    private fun instantiate(dynamicsProviders: List<Pair<KClass<*>, ServiceProvider>>): Container {
        return Container(
            config,
            globalServices
                // add singleton providers for all services marked as dynamic
                .plus(semiDynamicServices.map { it to SingletonServiceProvider.ofConstructor(it) })
                // add constant providers for the provided dynamic instances
                .plus(dynamicsProviders)
        )
    }

    private fun validate() {

        // create a container with dummy entries for the mandatory dynamic services
        val container = instantiate(
            mandatoryDynamics.map { it to ConstantServiceProvider(it) }
        )

        // validate all service providers
        val errors = container.providers
            .mapValues { (_, v) -> v.validate(container) }
            .filterValues { it.isNotEmpty() }
            .toList()
            .mapIndexed { serviceIdx, (cls, errors) ->
                "${serviceIdx + 1}. Service '${cls.qualifiedName}'\n" + errors.joinToString("\n") { "    -> $it" }
            }

        if (errors.isNotEmpty()) {

            val err = "Kontainer is inconsistent!\n\n" +
                    "Problems:\n\n" +
                    errors.joinToString("\n") + "\n\n" +
                    "Config values:\n\n" +
                    config.map { (k, v) -> "${k.padEnd(10)} => '$v' (${v::class.qualifiedName})" }.joinToString("\n") + "\n"

            error(err)
        }
    }
}

/**
 * The container
 */
data class Container(val config: Map<String, Any>, val providers: Map<KClass<*>, ServiceProvider>) {

    // getting services ////////////////////////////////////////////////////////////////////////////////////////////////////

    fun <T : Any> has(cls: KClass<T>): Boolean = providers[cls] != null

    inline fun <reified T : Any> get() = get(T::class)

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(cls: KClass<T>): T = (providers[cls] ?: error("Service $cls not found")).provide(this) as T


    // getting parameters //////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Check if there is a config value with the given [id] that is of the given [type]
     */
    fun hasConfig(id: String, type: KClass<*>) = config[id].let { it != null && it::class == type }

    /**
     * Get a config value by its [id]
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getConfig(id: String) = config[id] as T
}


////////////////////////////////////////////////////////////////////////////////////////////////////

class BrokenService(data: LocalDateTime, srvs: List<DynamicService>) {
    private var counter = 0
    fun inc() = ++counter
}

class ConfigInjectingService(private val devMode: Boolean) {
    fun get() = devMode
}

class GlobalService {
    private var counter = 0
    fun inc() = ++counter
}

interface DynamicService {
    fun inc(): Int
}

class DynamicServiceImpl(private var counter: Int) : DynamicService {
    override fun inc() = ++counter
}

class UsingDynamicService(val injected: DynamicService)


fun main() {

    val builder = ContainerBuilder {

        config("devMode", true)

        singleton<GlobalService>()
        singleton<UsingDynamicService>()

        singleton<ConfigInjectingService>()

        dynamic<DynamicService>()
    }

    val blueprint = builder.build()


    println("== Registered =========================")
    println(builder.classes)
    println("== Blueprint ==========================")
    println(blueprint)

    println("== Container 1 ==========================")

    blueprint.useWith(
        DynamicServiceImpl(100)
    ).apply {

        get<GlobalService>().apply {
            println("Global: ${inc()}")
        }

        get<UsingDynamicService>().apply {
            println("Dynamic: ${injected.inc()}")
        }

        get<ConfigInjectingService>().apply {
            println("Config devMode: ${get()}")
        }
    }

    println("== Container 2 ==========================")

    blueprint.useWith(
        DynamicServiceImpl(200)
    ).apply {

        get<GlobalService>().apply {
            println("Global: ${inc()}")
        }

        get<UsingDynamicService>().apply {
            println("Dynamic: ${injected.inc()}")
        }
    }
}
