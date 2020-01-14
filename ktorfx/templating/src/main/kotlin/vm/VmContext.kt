package de.peekandpoke.ktorfx.templating.vm

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
suspend fun PipelineContext<Unit, ApplicationCall>.respond(vm: ViewModel) {

    try {
        val view = vm.handle(call)

        val status = HttpStatusCode.OK

        call.respondHtmlTemplate(
            defaultTemplate,
            status,
            { content { view.render(this) } }
        )
    } catch (action: ViewModelAction) {
        when (action) {
            is ViewModelAction.Reload -> call.respondRedirect(call.request.uri, action.permanent)
        }
    }
}


fun viewModel(fn: suspend (vm: ViewModelBuilder) -> View) = ViewModel(fn)

class ViewModel(private val fn: suspend (vm: ViewModelBuilder) -> View) {

    suspend fun handle(call: ApplicationCall): View = fn(ViewModelBuilder(call))
}

class ViewModelBuilder internal constructor(val call: ApplicationCall, val path: String = "") {

    fun view(fn: FlowContent.() -> Any?) = View(fn)

    fun reload(permanent: Boolean = false) {
        throw ViewModelAction.Reload(permanent)
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

class View(private val block: FlowContent.() -> Any?) : ViewModelAction() {

    fun render(flow: FlowContent) {
        flow.block()
    }
}

sealed class ViewModelAction : Throwable() {
    data class Reload(val permanent: Boolean = false) : ViewModelAction()
}
