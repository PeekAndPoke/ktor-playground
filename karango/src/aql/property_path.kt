package de.peekandpoke.karango.aql

data class PropertyPath<T>(private val previous: PropertyPath<*>?, private val current: Step<T>) : Expression<T>, Aliased {

    abstract class Step<T>(private val type: TypeRef<T>) : Expression<T> {
        override fun getType() = type;
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

    companion object {
        inline fun <reified T> start(root: Expression<T>) = PropertyPath(null, ExprStep(root))
    }

    inline fun <reified NT> append(prop: String) = PropertyPath(this, PropStep<NT>(prop, typeRef()))

    inline fun <reified NT> contract() = PropertyPath<NT>(this, ArrayOpStep("[*]", typeRef()))
    inline fun <reified NT> expand() = PropertyPath<NT>(this, ArrayOpStep("[**]", typeRef()))

    override fun getType() = current.getType()

    override fun printAql(p: AqlPrinter) {

        previous?.apply { p.append(this) }

        p.append(current)
    }

    override fun getAlias() = AqlPrinter.sandbox { it.append(this) }
}


interface ArrayExpansion
object `*` : ArrayExpansion

interface ArrayContraction
object `**` : ArrayContraction

@Suppress("UNUSED_PARAMETER")
inline operator fun <reified T> PropertyPath<List<T>>.get(`*`: ArrayExpansion) = contract<T>()

@Suppress("UNUSED_PARAMETER")
inline operator fun <reified T> PropertyPath<T>.get(`**`: ArrayContraction) = expand<List<T>>()

