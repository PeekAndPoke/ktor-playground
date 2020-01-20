package de.peekandpoke.ktorfx.webresources

import de.peekandpoke.ultra.common.Lookup
import de.peekandpoke.ultra.logging.Log
import kotlin.reflect.KClass

/**
 * Main class that collects all [WebResourceGroup]
 */
class WebResources(
    private val log: Log,
    private val groups: Lookup<WebResourceGroup>
) {

    // TODO: when initializing, check that all "local" resources all present

    init {
        groups.all().forEach {
            log.info("Resource group $it OK")
        }
    }

    /**
     * Get a resource group by its [cls]
     *
     * If the group is not present a [WebResourceGroupNotFound] exception is thrown
     */
    operator fun <T : WebResourceGroup> get(cls: KClass<T>): T = groups.getOrNull(cls)
        ?: throw WebResourceGroupNotFound("Resource group '${cls.qualifiedName}' is not present.")
}
