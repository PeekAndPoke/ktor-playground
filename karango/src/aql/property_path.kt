package de.peekandpoke.karango.aql

import de.peekandpoke.karango.aql.PropertyPath.Companion.start
import de.peekandpoke.ultra.vault.TypeRef
import de.peekandpoke.ultra.vault.kType
import de.peekandpoke.ultra.vault.unList

typealias L1<T> = List<T>
typealias L2<T> = List<List<T>>
typealias L3<T> = List<List<List<T>>>
typealias L4<T> = List<List<List<List<T>>>>
typealias L5<T> = List<List<List<List<List<T>>>>>

data class PropertyPath<P, T>(private val previous: PropertyPath<*, *>?, private val current: Step<T>) : Expression<T>, Aliased {

    companion object {
        fun <T> start(root: Expression<T>) = PropertyPath<T, T>(null, ExprStep(root))
    }

    abstract class Step<T>(private val type: TypeRef<T>) : Expression<T> {
        override fun getType() = type
    }

    class PropStep<T>(private val name: String, type: TypeRef<T>) : Step<T>(type) {
        override fun printAql(p: AqlPrinter) = p.append(".").name(name)
    }

    class ExprStep<T>(private val expr: Expression<T>) : Step<T>(expr.getType()) {
        override fun printAql(p: AqlPrinter) = p.append(expr)
    }

    class ArrayOpStep<T>(private val op: String, type: TypeRef<T>) : Step<T>(type) {
        override fun printAql(p: AqlPrinter) = p.append(op)
    }

    override fun getAlias() = this.printQuery()

    override fun getType() = current.getType()

    override fun printAql(p: AqlPrinter) {

        previous?.apply { p.append(this) }

        p.append(current)
    }

    inline fun <NF, reified NT> append(prop: String) = PropertyPath<NF, NT>(this, PropStep(prop, kType()))

    fun <NP> expand() = PropertyPath<NP, T>(this, ArrayOpStep("[*]", current.getType()))

    @Suppress("UNCHECKED_CAST")
    fun <NT> contract() = PropertyPath<P, NT>(this, ArrayOpStep("[**]", (current.getType() as TypeRef<List<NT>>).unList))
}

interface ArrayExpansion

@Suppress("ClassName")
object `*` : ArrayExpansion

interface ArrayContraction

@Suppress("ClassName", "unused")
object `**` : ArrayContraction

@Suppress("UNUSED_PARAMETER")
operator fun <F, L : List<F>, T> PropertyPath<L, List<T>>.get(`*`: ArrayExpansion) = expand<F>()

@Suppress("UNUSED_PARAMETER")
operator fun <F, T> PropertyPath<F, L2<T>>.get(`**`: ArrayContraction) = contract<L1<T>>()

@Suppress("UNUSED_PARAMETER")
inline operator fun <reified T> Expression<List<T>>.get(`*`: ArrayExpansion) = start(this).expand<T>()
