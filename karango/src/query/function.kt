package de.peekandpoke.karango.query

import de.peekandpoke.karango.Expression
import de.peekandpoke.karango.TypeRef

enum class AqlFunc {
    CONTAINS,
} 

interface FunctionCall<T> : Expression<T>

internal class FunctionCallImpl<T>(private val type: TypeRef<T>, private val func: AqlFunc, private val args: List<Expression<*>>) : FunctionCall<T> {
    
    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.append("${func.name}(").join(args).append(")")
}
