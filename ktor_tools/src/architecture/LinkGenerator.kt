package io.ultra.ktor_tools.architecture

import io.ktor.application.Application
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.locations

@KtorExperimentalLocationsAPI
abstract class LinkGenerator(private val mountPoint: String, private val app: Application) {
    fun linkTo(location: Any) = "$mountPoint${app.locations.href(location)}"
}
