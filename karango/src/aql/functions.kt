package de.peekandpoke.karango.aql

enum class AqlFunc {
    CONTAINS,
    DOCUMENT,
    NOT,
} 

interface FunctionCall<T> : Expression<T>

interface IterableFunctionCall<T> : IterableExpression<T>

open class FuncCall<T>(private val type: TypeRef<T>, private val func: AqlFunc, private val args: List<Expression<*>>) : FunctionCall<T> {
    
    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.append("${func.name}(").join(args).append(")")
}

open class IterableFuncCall<T>(
    private val name_: String, 
    private val type: TypeRef<T>, 
    private val func: AqlFunc, 
    private val args: List<Expression<*>>) : IterableFunctionCall<T> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.append("${func.name}(").join(args).append(")")
}

internal class BoolFuncCall(func: AqlFunc, args: List<Expression<*>>) : FuncCall<Boolean>(typeRef(), func, args)
