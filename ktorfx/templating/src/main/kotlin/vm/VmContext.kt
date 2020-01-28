package de.peekandpoke.ktorfx.templating.vm

import de.peekandpoke.ktorfx.broker.TypedRoute
import de.peekandpoke.ktorfx.broker.typedRouteRenderer
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.ktorfx.templating.defaultTemplate
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.HttpStatusCode
import io.ktor.request.uri
import io.ktor.response.respondRedirect
import io.ktor.util.pipeline.PipelineContext
import kotlinx.html.FlowContent

/**
 * Responds with the default template
 */
suspend fun PipelineContext<Unit, ApplicationCall>.respond(vm: ViewModel, customize: SimpleTemplate.() -> Unit = {}) {

    try {
        val view = vm.handle(call)
        val status = HttpStatusCode.OK

        call.respondHtmlTemplate(
            defaultTemplate,
            status,
            {
                customize()

                content { view.render(this) }
            }
        )

    } catch (action: ViewModelAction) {
        when (action) {
            is ViewModelAction.Reload -> call.respondRedirect(call.request.uri)

            is ViewModelAction.Redirect -> call.respondRedirect(action.uri)
        }
    }
}


fun viewModel(fn: suspend (vm: ViewModelBuilder) -> View) = ViewModel(fn)

class ViewModel(private val fn: suspend (vm: ViewModelBuilder) -> View) {

    suspend fun handle(call: ApplicationCall): View = fn(ViewModelBuilder(call))
}

class ViewModelBuilder internal constructor(val call: ApplicationCall, val path: String = "") {

    fun view(fn: FlowContent.() -> Any?) = View(this, fn)

    fun <T : Any> route(route: TypedRoute.Bound<T>) = call.typedRouteRenderer(route)

    fun reload(): Nothing {
        throw ViewModelAction.Reload
    }

    fun redirect(uri: String): Nothing {
        throw ViewModelAction.Redirect(uri)
    }

    suspend fun child(name: String, block: suspend (vm: ViewModelBuilder) -> View): View {

        return block(
            ViewModelBuilder(call, appendToPath(name))
        )
    }

    private fun appendToPath(name: String) = when {
        path.isEmpty() -> name
        else -> "$path.$name"
    }
}

class View(private val vmb: ViewModelBuilder, private val block: FlowContent.() -> Any?) {

    val path get() = vmb.path

    fun render(flow: FlowContent) {
        flow.block()
    }
}

sealed class ViewModelAction : Throwable() {
    data class Redirect(val uri: String) : ViewModelAction()
    object Reload : ViewModelAction()
}
