package io.ultra.ktor_tools.architecture

import io.ktor.application.Application
import io.ktor.routing.Route

abstract class Module(val application: Application) {

    abstract fun mount(mountPoint: Route): Any
}
