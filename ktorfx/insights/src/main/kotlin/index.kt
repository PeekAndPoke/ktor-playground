package de.peekandpoke.ktorfx.insights

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.insights.collectors.KontainerCollector
import de.peekandpoke.ktorfx.insights.collectors.PhaseCollector
import de.peekandpoke.ktorfx.insights.collectors.RequestCollector
import de.peekandpoke.ktorfx.insights.collectors.ResponseCollector
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.request.httpMethod
import io.ktor.request.uri
import kotlin.system.measureNanoTime

val KtorFX_Insights = module {

    singleton(Insights::class)
    singleton(InsightsMapper::class)
    instance(InsightsRepository::class, InsightsFileRepository())

    dynamic0 { RequestCollector() }
    dynamic0 { ResponseCollector() }
    dynamic0 { KontainerCollector() }
    dynamic0 { PhaseCollector() }
}


fun Application.instrumentWithInsights() {

    intercept(ApplicationCallPipeline.Setup) {

        val ns = measureNanoTime { proceed() }

        kontainer.use(Insights::class) {

            use(PhaseCollector::class) { record("Setup", ns) }

            use(RequestCollector::class) { record(call) }
            use(ResponseCollector::class) { record(call) }
            use(KontainerCollector::class) { record(kontainer) }

            done()
        }

        log.debug("${call.request.httpMethod.value} ${call.request.uri} took ${ns / 1_000_000.0} ms")
    }

    intercept(ApplicationCallPipeline.Monitoring) {

        val ns = measureNanoTime { proceed() }

        kontainer.use(Insights::class) {
            use(PhaseCollector::class) { record("Monitoring", ns) }
        }
    }

    intercept(ApplicationCallPipeline.Features) {

        val ns = measureNanoTime { proceed() }

        kontainer.use(Insights::class) {
            use(PhaseCollector::class) { record("Features", ns) }
        }
    }

    intercept(ApplicationCallPipeline.Call) {

        val ns = measureNanoTime { proceed() }

        kontainer.use(Insights::class) {
            use(PhaseCollector::class) { record("Call", ns) }
        }
    }

    intercept(ApplicationCallPipeline.Fallback) {

        val ns = measureNanoTime { proceed() }

        kontainer.use(Insights::class) {
            use(PhaseCollector::class) { record("Fallback", ns) }
        }
    }
}
