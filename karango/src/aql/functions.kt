package de.peekandpoke.karango.aql

enum class AqlFunc {
    CONTAINS,
    DOCUMENT,
    NOT,
}

interface FuncCall<T> : Expression<T> {

    companion object {

        fun <X> of(type: TypeRef<X>, func: AqlFunc, args: List<Expression<*>>): FuncCall<X> = FuncCallImpl(type, func, args)

        fun bool(func: AqlFunc, args: List<Expression<*>>): FuncCall<Boolean> = FuncCallImpl(TypeRef.Boolean, func, args)
    }
}

internal class FuncCallImpl<T>(private val type: TypeRef<T>, private val func: AqlFunc, private val args: List<Expression<*>>) : FuncCall<T> {

    override fun getType() = type
    override fun printAql(p: AqlPrinter) = p.append("${func.name}(").join(args).append(")")
}

