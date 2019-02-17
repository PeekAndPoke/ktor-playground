package de.peekandpoke.karango.query

import de.peekandpoke.karango.Named
import de.peekandpoke.karango.Statement

internal class ReturnNamed<T>(private val named: Named, private val type: Class<T>) : Statement<T> {
    
    override fun getReturnType() = type

    override fun printStmt(p: AqlPrinter) = p.append("RETURN ").identifier(named).appendLine()
}

class ReturnDocumentById<T>(private val id: String, private val type: Class<T>) : Statement<T> {

    override fun getReturnType() = type

    override fun printStmt(p: AqlPrinter) =
        p.append("RETURN DOCUMENT (").value("id", id).append(")").appendLine()
}

class ReturnDocumentsByIds<T>(private val ids: List<String>, private val type: Class<T>) : Statement<T> {

    override fun getReturnType() = type

    override fun printStmt(p: AqlPrinter) =
        p.append("FOR x IN DOCUMENT (").value("ids", ids).append(") RETURN x").appendLine()
}
