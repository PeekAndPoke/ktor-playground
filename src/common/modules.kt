package de.peekandpoke.common

import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.locations
import io.ktor.routing.Route
import io.ktor.routing.application

@KtorExperimentalLocationsAPI
abstract class LinkGenerator(private val mountPoint: Route) {
    fun linkTo(location: Any) = "$mountPoint${mountPoint.application.locations.href(location)}"
}
