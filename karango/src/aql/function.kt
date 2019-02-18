package de.peekandpoke.karango.aql

enum class AqlFunc {
    CONTAINS,
    NOT,
} 

interface FunctionCall<T> : Expression<T>

internal open class FunctionCallImpl<T>(private val type: TypeRef<T>, private val func: AqlFunc, private val args: List<Expression<*>>) : FunctionCall<T> {
    
    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.append("${func.name}(").join(args).append(")")
}

internal class BoolFuncCall(func: AqlFunc, args: List<Expression<*>>) : FunctionCallImpl<Boolean>(typeRef(), func, args)
