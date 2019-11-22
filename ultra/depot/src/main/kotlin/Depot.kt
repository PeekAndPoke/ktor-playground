package de.peekandpoke.ultra.depot

import de.peekandpoke.ultra.common.Lookup
import kotlin.reflect.KClass

class Depot(private val drivers: Lookup<DepotRepository>) {

    fun <T : DepotRepository> get(driver: KClass<T>): T = drivers.getOrNull(driver)
        ?: throw DepotException("There is no driver '${driver.qualifiedName}' registered in the depot")

    @Suppress("UNCHECKED_CAST")
    fun get(name: String): DepotRepository = drivers.all().firstOrNull { it.name == name }
        ?: throw DepotException("There is no driver '$name' registered in the depot")

    fun all() = drivers.all()
}
