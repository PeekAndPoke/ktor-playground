package de.peekandpoke

import de.peekandpoke.ultra.kontainer.kontainer


class BrokenService(srvs: List<DynamicService>) {
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

    val blueprint = kontainer {

        config("devMode", true)

        singleton<GlobalService>()
        singleton<UsingDynamicService>()

        singleton<ConfigInjectingService>()

        singleton<BrokenService>()

        dynamic<DynamicService>()
    }

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
