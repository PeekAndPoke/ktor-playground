package de.peekandpoke.karango.query

import de.peekandpoke.karango.Expression
import de.peekandpoke.karango.Named
import de.peekandpoke.karango.TypeRef

class ReturnNamed<T>(private val named: Named, private val type: TypeRef<T>) : Expression<T> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter) =
        p.append("RETURN ").name(named).appendLine()
}

class ReturnIterator<T>(private val named: Named, private val type: TypeRef<T>) : Expression<T> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter) =
        p.append("RETURN ").iterator(named).appendLine()
}

class ReturnDocumentById<T>(private val id: String, private val type: TypeRef<T>) : Expression<T> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter) =
        p.append("RETURN DOCUMENT (").value("id", id).append(")").appendLine()
}

class ReturnDocumentsByIds<T>(private val ids: List<String>, private val type: TypeRef<T>) : Expression<T> {

    override fun getType() = type

    override fun printAql(p: AqlPrinter) =
        p.append("FOR x IN DOCUMENT (").value("ids", ids).append(") RETURN x").appendLine()
}
