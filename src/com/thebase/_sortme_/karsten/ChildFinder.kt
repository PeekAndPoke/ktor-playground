package com.thebase._sortme_.karsten

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

/**
 * Helper class that descends into an object to find all children and grand-children of the given type
 */
class ChildFinder<C : Any, T : Any> private constructor(
    private val searchedClass: KClass<C>,
    private val inTarget: T,
    private val filter: (C) -> Boolean
) {

    private val visited = mutableSetOf<Any>()
    private val found = mutableSetOf<C>()

    companion object {
        fun <C : Any, T : Any> find(cls: KClass<C>, inTarget: T) = find(cls, inTarget) { true }

        fun <C : Any, T : Any> find(cls: KClass<C>, inTarget: T, filter: (C) -> Boolean) = ChildFinder(cls, inTarget, filter).run()
    }

    fun run(): List<C> {
        visited.clear()
        found.clear()

        visit(inTarget)

        return found.toList()
    }

    private fun visit(target: Any?) {

        if (target == null || visited.contains(target)) {
            return
        }

        visited.add(target)

        @Suppress("UNCHECKED_CAST")
        if (target::class == searchedClass && filter(target as C)) {
            found.add(target)
            return
        }

        when {
            target is List<*> -> visitCollection(target)
            target is Map<*, *> -> visitCollection(target.values)
            else -> visitObject(target)
        }
    }

    private fun visitCollection(collection: Collection<*>) = collection.forEach { visit(it) }

    private fun visitObject(target: Any) {
        val reflect = target::class

        // Blacklist internal java and kotlin classes
        val fqn = reflect.qualifiedName
        if (fqn != null && (fqn.startsWith("java.") || fqn.startsWith("kotlin."))) {
            return
        }

        reflect.memberProperties
            .forEach {
                visit(
                    it.javaField?.apply { it.isAccessible = true }?.get(target)
                )
            }
    }
}

