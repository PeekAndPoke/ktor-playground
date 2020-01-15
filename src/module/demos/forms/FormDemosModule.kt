package de.peekandpoke.module.demos.forms

import com.fasterxml.jackson.databind.ObjectMapper
import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ktorfx.broker.getOrPost
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.common.texts.forms
import de.peekandpoke.ktorfx.formidable.*
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.module.demos.forms.domain.CommaSeparatedFields
import de.peekandpoke.module.demos.forms.domain.SimpleFields
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.call
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.html.a
import kotlinx.html.id
import kotlinx.html.pre

fun KontainerBuilder.formDemos() = module(FormDemosModule)

val FormDemosModule = module {
    singleton(FormDemosRoutes::class)
    singleton(FormDemos::class)
}

class FormDemosRoutes(converter: OutgoingConverter) : Routes(converter, "/demos/forms") {

    val index = route("")

    val simpleFields = route("/simple-fields")

    val commaSeparated = route("/comma-separated")

    val listOfFields = route("/list-of-fields")
}

class FormDemos(val routes: FormDemosRoutes) {

    private val jsonWriter by lazy {
        ObjectMapper().writerWithDefaultPrettyPrinter()
    }

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

        getOrPost(routes.simpleFields) {

            val (data, form) = SimpleFields().all(call)

            respond {
                breadCrumbs = listOf(FormDemosMenu.SimpleFields)

                content {
                    ui.header H3 {
                        if (form.isSubmitted()) {
                            if (form.isValid()) {
                                icon.green.check()
                            } else {
                                icon.red.exclamation()
                            }
                        }
                        +"Simple form fields"
                    }

                    ui.grid {
                        ui.ten.wide.column {

                            formidable(i18n, form) {

                                ui.four.fields {
                                    textInput(form.boolean, "Boolean")
                                    textInput(form.booleanOptional, "Boolean optional")
                                    textInput(form.byte, "Byte")
                                    textInput(form.byteOptional, "Byte optional")
                                }

                                ui.four.fields {
                                    textInput(form.char, "Char")
                                    textInput(form.charOptional, "Char optional")
                                    textInput(form.short, "Short")
                                    textInput(form.shortOptional, "Short optional")
                                }

                                ui.four.fields {
                                    textInput(form.int, "Int")
                                    textInput(form.intOptional, "Int optional")
                                    textInput(form.long, "Long")
                                    textInput(form.longOptional, "Long optional")
                                }

                                ui.four.fields {
                                    textInput(form.float, "Float")
                                    textInput(form.floatOptional, "Float optional")
                                    textInput(form.double, "Double")
                                    textInput(form.doubleOptional, "Double optional")
                                }

                                ui.four.fields {
                                    textInput(form.string, "String")
                                    textInput(form.stringOptional, "String optional")
                                }

                                ui.four.fields {
                                    textInput(form.bigInteger, "BigInteger")
                                    textInput(form.bigIntegerOptional, "BigInteger optional")
                                    textInput(form.bigDecimal, "BigDecimal")
                                    textInput(form.bigDecimalOptional, "BigDecimal optional")
                                }

                                ui.button Submit { +t { forms.submit } }
                            }
                        }

                        ui.six.wide.column {
                            pre { +jsonWriter.writeValueAsString(data) }
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

                    samples.forEach { (description, data, form) ->

                        a { id = form.getId().asFormId }

                        ui.dividing.given(form.isSubmitted()) { green }.header H3 { +description }

                        ui.two.column.grid {
                            ui.column {
                                formidable(i18n, form, { action = "#${form.getId().asFormId}" }) {
                                    textInput(it.field)
                                }
                            }

                            ui.column {
                                pre { +data.toString() }
                            }
                        }
                    }
                }
            }
        }

        getOrPost(routes.listOfFields) {

            data class ListOfStrings(
                var strings: MutableList<String> = mutableListOf("a", "b")
            )

            val listWithStrings = ListOfStrings()

            class ListWithStringsForm(target: ListOfStrings) : Form("strings") {

                val strings = list(target::strings) {
                    field(it::value).acceptsNonEmpty()
                }
            }

            val listWithStringsForm = ListWithStringsForm(listWithStrings).apply { submit(call) }

            data class SomeObject(var string: String, var int: Int)

            data class ListWithObjects(
                var objects: MutableList<SomeObject> = mutableListOf(
                    SomeObject("a", 1),
                    SomeObject("b", 2),
                    SomeObject("c", 3)
                )
            )

            class SomeObjectForm(target: SomeObject) : Form("some-object") {
                val string = field(target::string).acceptsNonEmpty()
                val int = field(target::int).resultingInRange(0..200)
            }

            class ListWithObjectsForm(target: ListWithObjects) : Form("list-with-objects") {

                val objects = list(target::objects) { element ->
                    subForm(
                        SomeObjectForm(element.value)
                    )
                }
            }

            val listWithObjects = ListWithObjects()

            val listWithObjectsForm = ListWithObjectsForm(listWithObjects).apply { submit(call) }

            respond {
                breadCrumbs = listOf(FormDemosMenu.ListOfFields)

                content {

                    ui.dividing.header H3 { +"List of strings" }

                    ui.grid {

                        ui.ten.wide.column {
                            formidable(i18n, listWithStringsForm) {

                                it.strings.forEachIndexed { idx, child ->
                                    textInput(child, label = "Field ${idx + 1}")
                                }

                                ui.button Submit { +t { forms.submit } }
                            }
                        }

                        ui.six.wide.column {
                            pre {
                                +jsonWriter.writeValueAsString(listWithStrings)
                            }
                        }
                    }

                    ui.dividing.header H3 { +"List of objects" }

                    ui.grid {

                        ui.ten.wide.column {
                            formidable(i18n, listWithObjectsForm) {

                                it.objects.forEachIndexed { idx, child ->
                                    ui.two.fields {
                                        textInput(child.string, label = "Field ${idx + 1} String")
                                        textInput(child.int, label = "Field ${idx + 1} Int")
                                    }
                                }

                                ui.button Submit { +t { forms.submit } }
                            }
                        }

                        ui.six.wide.column {
                            pre {
                                +jsonWriter.writeValueAsString(listWithObjects)
                            }
                        }
                    }
                }
            }
        }
    }
}
