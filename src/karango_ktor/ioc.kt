package de.peekandpoke.karango_ktor

import de.peekandpoke.karango.Db
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext

val iocDatabaseKey = AttributeKey<Db>("database")

inline val ApplicationCall.database: Db get() = attributes[iocDatabaseKey]

inline val PipelineContext<Unit, ApplicationCall>.database: Db get() = call.database

fun Attributes.provide(value: Db) = put(iocDatabaseKey, value)
