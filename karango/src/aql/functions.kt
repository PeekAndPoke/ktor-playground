package de.peekandpoke.karango.aql

enum class AqlFunc {
    CONCAT,
    CONTAINS,
    DOCUMENT,
    NOT,
    TO_BOOL,
    TO_NUMBER,
    TO_STRING,
    TO_ARRAY,
    TO_LIST,
}

fun <T> AqlFunc.call(type: TypeRef<T>, vararg args: Expression<*>) = FuncCall.of(type, this, args)

fun <T> AqlFunc.arrayCall(type: TypeRef<List<T>>, vararg args: Expression<*>) = FuncCall.array(this, type, args)

fun AqlFunc.boolCall(vararg args: Expression<*>) = FuncCall.bool(this, args)

fun AqlFunc.numberCall(vararg args: Expression<*>) = FuncCall.number(this, args)

fun AqlFunc.stringCall(vararg args: Expression<*>) = FuncCall.string(this, args)

interface FuncCall<T> : Expression<T> {

    companion object {

        fun <X> of(type: TypeRef<X>, func: AqlFunc, args: Array<out Expression<*>>): FuncCall<X> = FuncCallImpl(type, func, args)

        fun <T> array(func: AqlFunc, type: TypeRef<List<T>>, args: Array<out Expression<*>>): FuncCall<List<T>> = FuncCallImpl(type, func, args)
        
        fun bool(func: AqlFunc, args: Array<out Expression<*>>): FuncCall<Boolean> = FuncCallImpl(TypeRef.Boolean, func, args)
        
        fun number(func: AqlFunc, args: Array<out Expression<*>>): FuncCall<Number> = FuncCallImpl(TypeRef.Number, func, args)
        
        fun string(func: AqlFunc, args: Array<out Expression<*>>): FuncCall<String> = FuncCallImpl(TypeRef.String, func, args)
    }
}

internal class FuncCallImpl<T>(private val type: TypeRef<T>, private val func: AqlFunc, private val args: Array<out Expression<*>>) : FuncCall<T> {

    override fun getType() = type
    override fun printAql(p: AqlPrinter) = p.append("${func.name}(").join(args).append(")")
}

