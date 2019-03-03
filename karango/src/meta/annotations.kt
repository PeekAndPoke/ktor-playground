package de.peekandpoke.karango.meta


@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class EntityCollection(
    val collection: String,
    val defName: String = "" 
)
