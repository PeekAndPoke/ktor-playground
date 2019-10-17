package de.peekandpoke.module.demos.forms

import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ktorfx.broker.getOrPost
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.semanticui.formidable
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.module.demos.forms.domain.CommaSeparatedFields
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.call
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.html.a
import kotlinx.html.pre

fun KontainerBuilder.formDemos() = module(FormDemosModule)

val FormDemosModule = module {
    singleton(FormDemosRoutes::class)
    singleton(FormDemos::class)
}

class FormDemosRoutes(converter: OutgoingConverter) : Routes(converter, "/demos/forms") {

    val index = route("")

    val commaSeparated = route("/comma-separated")
}

class FormDemos(val routes: FormDemosRoutes) {

    fun Route.mount() {

        get(routes.index) {

            respond {

                breadCrumbs = listOf(FormDemosMenu.Index)

                content {
                    ui.header H3 { +"Form Demos" }

                    ui.list {
                        ui.item {
                            a(href = routes.commaSeparated) { +"Comma separated lists" }
                        }
                    }
                }
            }
        }

        getOrPost(routes.commaSeparated) {

            val samples = CommaSeparatedFields().all(call)

            respond {
                breadCrumbs = listOf(FormDemosMenu.CommaSeparated)

                content {

                    samples.forEach {
                        ui.dividing.header H3 { +it.first }

                        ui.two.column.grid {
                            ui.column {
                                formidable(i18n, it.third) {
                                    textInput(it.third.field)
                                }
                            }

                            ui.column {
                                pre { +it.second.toString() }
                            }
                        }
                    }
                }
            }
        }
    }
}
