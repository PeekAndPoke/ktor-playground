package com.thebase._sortme_.karsten

import io.ktor.application.ApplicationCall
import io.ktor.response.respondRedirect

suspend fun ApplicationCall.redirectToReferrer() = respondRedirect(
    this.request.headers["Referer"] ?: "",
    false
)
