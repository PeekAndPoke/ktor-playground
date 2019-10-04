package de.peekandpoke.ktorfx.insights

import de.peekandpoke.ktorfx.common.hasKontainer
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.insights.collectors.*
import de.peekandpoke.ktorfx.insights.gui.InsightsBarRenderer
import de.peekandpoke.ktorfx.insights.gui.InsightsGui
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiRoutes
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiWebResourceGroup
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.request.httpMethod
import io.ktor.request.uri
import io.ktor.routing.routing
import kotlin.system.measureNanoTime

val KtorFX_Insights = module {

    singleton(Insights::class)
    singleton(InsightsMapper::class)
    instance(InsightsRepository::class, InsightsFileRepository())

    // default collectors
    dynamic(RequestCollector::class)
    dynamic(ResponseCollector::class)
    dynamic(KontainerCollector::class)
    dynamic(PipelinePhasesCollector::class)
    dynamic(VaultCollector::class)

    // web resources and rendering
    singleton(InsightsGuiWebResourceGroup::class)
    dynamic(InsightsBarRenderer::class)

    // endpoints
    singleton(InsightsGuiRoutes::class)
    singleton(InsightsGui::class)
}


fun Application.instrumentWithInsights(gui: InsightsGui) {

    routing {
        gui.mount(this)
    }

    intercept(ApplicationCallPipeline.Setup) {

        val ns = measureNanoTime { proceed() }

        if (hasKontainer) {
            kontainer.use(Insights::class) {
                use(PipelinePhasesCollector::class) { record("Setup", ns) }

                finish(call)
            }
        }

        log.debug("${call.request.httpMethod.value} ${call.request.uri} took ${ns / 1_000_000.0} ms")
    }

    intercept(ApplicationCallPipeline.Monitoring) {

        val ns = measureNanoTime { proceed() }

        if (hasKontainer) {
            kontainer.use(Insights::class) {
                use(PipelinePhasesCollector::class) { record("Monitoring", ns) }
            }
        }
    }

    intercept(ApplicationCallPipeline.Features) {

        val ns = measureNanoTime { proceed() }

        if (hasKontainer) {
            kontainer.use(Insights::class) {
                use(PipelinePhasesCollector::class) { record("Features", ns) }
            }
        }
    }

    intercept(ApplicationCallPipeline.Call) {

        val ns = measureNanoTime { proceed() }

        if (hasKontainer) {
            kontainer.use(Insights::class) {
                use(PipelinePhasesCollector::class) { record("Call", ns) }
            }
        }
    }

    intercept(ApplicationCallPipeline.Fallback) {

        val ns = measureNanoTime { proceed() }

        if (hasKontainer) {
            kontainer.use(Insights::class) {
                use(PipelinePhasesCollector::class) { record("Fallback", ns) }
            }
        }
    }
}
