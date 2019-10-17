package de.peekandpoke.ktorfx.insights

import de.peekandpoke.ktorfx.common.hasKontainer
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.insights.collectors.*
import de.peekandpoke.ktorfx.insights.gui.*
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.request.httpMethod
import io.ktor.request.uri
import io.ktor.routing.Route
import io.ktor.routing.Routing
import io.ktor.routing.RoutingResolveTrace
import io.ktor.util.AttributeKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureNanoTime

fun KontainerBuilder.ktorFxInsights() = module(KtorFX_Insights)

val KtorFX_Insights = module {

    singleton(Insights::class)
    singleton(InsightsMapper::class)
    instance(InsightsRepository::class, InsightsFileRepository())

    // Default collectors
    dynamic(RequestCollector::class)
    dynamic(ResponseCollector::class)
    dynamic(RoutingCollector::class)
    dynamic(KontainerCollector::class)
    dynamic(PipelinePhasesCollector::class)
    dynamic(RuntimeCollector::class)
    dynamic(VaultCollector::class)

    // Insights Bar
    dynamic(InsightsBarRenderer::class)
    singleton(InsightsBarWebResources::class)

    // Insights Gui
    singleton(InsightsGuiWebResources::class)
    singleton(InsightsGuiRoutes::class)
    singleton(InsightsGui::class)
    prototype(InsightsGuiTemplate::class)
}

val RoutingTraceKey = AttributeKey<RoutingResolveTrace>("routing_resolve_trace")

/**
 * We need some special handling for recording the route trace
 *
 * The problem:
 *
 * The route tracers are called before the pipeline is executed.
 * This mean that we did not have the chance to inject the kontainer
 * into the call parameters yet.
 *
 * The solution:
 *
 * We put the [RoutingResolveTrace] in the call attributes.
 * And the [RoutingCollector] will pick it up in its finish() method.
 *
 * NOTICE:
 *
 * This handler should only be registered once in the global routing { } block of the
 * application.
 */
fun Routing.registerInsightsRouteTracer() {

    trace {
        it.call.attributes.put(RoutingTraceKey, it)
    }
}

/**
 * Applies insights instrumentation to the Pipeline
 */
fun Route.instrumentWithInsights() {

    intercept(ApplicationCallPipeline.Setup) {

        if (hasKontainer) {
            kontainer.use(Insights::class) {}
        }

        val ns = measureNanoTime { proceed() }

        if (hasKontainer) {
            kontainer.use(Insights::class) {
                use(PipelinePhasesCollector::class) { record("Setup", ns) }

                launch {
                    delay(1)
                    finish(call)
                }
            }
        }

        application.log.debug("${call.request.httpMethod.value} ${call.request.uri} took ${ns / 1_000_000.0} ms")
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
