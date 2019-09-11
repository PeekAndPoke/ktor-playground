package de.peekandpoke.karango

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import de.peekandpoke.karango.jackson.EntityRefDeserializer
import de.peekandpoke.karango.jackson.EntityRefSerializer


@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class Karango


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = EntityRefSerializer::class)
@JsonDeserialize(using = EntityRefDeserializer::class)
annotation class Ref

