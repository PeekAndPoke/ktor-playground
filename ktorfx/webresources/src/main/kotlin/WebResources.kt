package de.peekandpoke.ktorfx.webresources

import de.peekandpoke.ultra.common.Lookup
import kotlin.reflect.KClass

/**
 * Main class that collects all [WebResourceGroup]
 */
class WebResources(private val groups: Lookup<WebResourceGroup>) {

    // TODO: when initializing, check that all "local" resources all present

    /**
     * Get a resource group by its [cls]
     *
     * If the group is not present a [WebResourceGroupNotFound] exception is thrown
     */
    operator fun <T : WebResourceGroup> get(cls: KClass<T>): T = groups.getOrNull(cls)
        ?: throw WebResourceGroupNotFound("Resource group '${cls.qualifiedName}' is not present.")
}
