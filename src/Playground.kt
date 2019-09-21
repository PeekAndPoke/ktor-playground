package de.peekandpoke

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor


class GlobalService {
    private var counter = 0
    fun inc() = ++counter
}

class DynamicService(private var counter: Int) {
    fun inc() = ++counter
}

class UsingDynamicService(val injected: DynamicService)

////////////////////////////////////////////////////////////////////////////////////////////////////

interface ParameterProvider {

    operator fun invoke(container: Container): Any

    data class ForSimpleObject(private val cls: KClass<*>) : ParameterProvider {

        override operator fun invoke(container: Container): Any {
            return container.get(cls)
        }
    }

    companion object {
        fun of(owner: KClass<*>, parameter: KParameter): ParameterProvider {

            val parameterClass = parameter.type.classifier as KClass<*>

            return when {
                parameterClass.typeParameters.isEmpty() -> ForSimpleObject(parameterClass)

                else -> error(
                    """
                    Cannot inject a parameter ${owner.qualifiedName}::${parameter.name} of type ${parameterClass.qualifiedName}                    
                """.trimIndent()
                )
            }
        }
    }
}

interface ServiceProvider {
    fun provide(container: Container): Any
}

data class ConstantServiceProvider(private val instance: Any) : ServiceProvider {
    override fun provide(container: Container): Any = instance
}

data class SingletonServiceProvider constructor(
    private val owner: KClass<*>,
    private val creator: KFunction<*>,
    private val params: List<ParameterProvider>
) : ServiceProvider {

    companion object {
        fun of(cls: KClass<*>) = SingletonServiceProvider(
            cls,
            cls.primaryConstructor!!,
            cls.primaryConstructor!!.parameters.map { ParameterProvider.of(cls, it) }
        )
    }

    private var instance: Any? = null

    override fun provide(container: Container): Any {

        if (instance != null) {
            return instance!!
        }

        @Suppress("UNCHECKED_CAST")
        instance = creator.call(*params.map { it(container) }.toTypedArray())

        return instance!!
    }
}

enum class InjectionType {
    Singleton,
    Dynamic
}

class ContainerBuilder(builder: ContainerBuilder.() -> Unit = {}) {

    internal val classes = mutableMapOf<KClass<*>, InjectionType>()

    init {
        builder(this)
    }

    fun <T : Any> add(def: KClass<T>, type: InjectionType) = apply { classes[def] = type }

    inline fun <reified T : Any> singleton() = add(T::class, InjectionType.Singleton)

    inline fun <reified T : Any> dynamic() = add(T::class, InjectionType.Dynamic)

    fun build(): ContainerBlueprint = ContainerBlueprint(classes)
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
            cls.primaryConstructor!!.parameters.forEach {
                tmp.getOrPut(it.type.classifier as KClass<*>) { mutableSetOf() }.add(cls)
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

        return current
    }
}

data class ContainerBlueprint(val classes: Map<KClass<*>, InjectionType>) {

    /**
     * A set of all classes that need to passed to [useWith]
     */
    private val mandatoryDynamics: Set<KClass<*>> = classes.filterValues { it == InjectionType.Dynamic }.keys

    /**
     * Dependency look up
     */
    private val dependencyLookUp = DependencyLookUp(classes.keys)

    /**
     * Build up a set of all dynamic services
     */
    private val dynamicServices: Set<KClass<*>> = dependencyLookUp.getAllDependents(mandatoryDynamics)

    /**
     * Global services are the services that have no dependency to any of the dynamic services
     */
    private val globalServices: Map<KClass<*>, ServiceProvider> = classes
        .filterKeys { !dynamicServices.contains(it) }
        .mapValues { (k, _) -> SingletonServiceProvider.of(k) }

    // TODO: validate the container
    //       1. check if all services are present
    //       2. check for cyclic dependencies


    fun useWith(vararg dynamics: Any): Container {
        // TODO: check that all dynamic are present

        return Container(
            globalServices
                // add singleton providers for all services marked as dynamic
                .plus(dynamicServices.map { it to SingletonServiceProvider.of(it) })
                // add constant providers for the provided dynamic instances
                .plus(dynamics.map { it::class to ConstantServiceProvider(it) })
        )
    }
}

/**
 * The container
 */
data class Container(val providers: Map<KClass<*>, ServiceProvider>) {

    inline fun <reified T : Any> get() = get(T::class)

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(cls: KClass<T>): T = (providers[cls] ?: error("Service $cls not found")).provide(this) as T
}


fun main() {

    val builder = ContainerBuilder {
        singleton<GlobalService>()
        singleton<UsingDynamicService>()

        dynamic<DynamicService>()
    }

    val blueprint = builder.build()


    println("== Registered =========================")
    println(builder.classes)
    println("== Blueprint ==========================")
    println(blueprint)

    println("== Container 1 ==========================")

    blueprint.useWith(
        DynamicService(100)
    ).apply {


        get<GlobalService>().apply {
            println("Global: ${inc()}")
        }

        get<UsingDynamicService>().apply {
            println("Dynamic: ${injected.inc()}")
        }
    }

    println("== Container 2 ==========================")

    blueprint.useWith(
        DynamicService(200)
    ).apply {

        get<GlobalService>().apply {
            println("Global: ${inc()}")
        }

        get<UsingDynamicService>().apply {
            println("Dynamic: ${injected.inc()}")
        }
    }
}
