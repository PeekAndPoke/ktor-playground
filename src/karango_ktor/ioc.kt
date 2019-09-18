package de.peekandpoke.karango_ktor

import de.peekandpoke.ultra.vault.Database
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext

val iocDatabaseKey = AttributeKey<Database>("database")

inline val ApplicationCall.database: Database get() = attributes[iocDatabaseKey]

inline val PipelineContext<Unit, ApplicationCall>.database: Database get() = call.database

fun Attributes.provide(value: Database) = put(iocDatabaseKey, value)
