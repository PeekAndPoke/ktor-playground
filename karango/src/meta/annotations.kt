package de.peekandpoke.karango.meta

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import de.peekandpoke.karango.EntityRefDeserializer
import de.peekandpoke.karango.EntityRefSerializer


@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class EntityCollection(
    val collection: String,
    val defName: String = ""
)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = EntityRefSerializer::class)
@JsonDeserialize(using = EntityRefDeserializer::class)
annotation class Ref

