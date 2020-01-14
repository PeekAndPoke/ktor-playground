package de.peekandpoke._sortme_.karsten

import io.ktor.application.ApplicationCall
import io.ktor.request.uri
import io.ktor.response.respondRedirect

fun String.camelCaseSplit() = "(?<=[a-z])(?=[A-Z])".toRegex().split(this)

fun String.camelCaseDivide(divider: String = " ") = camelCaseSplit().joinToString(divider)


fun <E> List<E>.replaceAt(idx: Int, element: E) = toMutableList().apply { set(idx, element) }.toList()


suspend fun ApplicationCall.respondReload() = respondRedirect(request.uri, false)
