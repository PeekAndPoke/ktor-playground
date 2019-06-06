package de.peekandpoke.mutator.meta

import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import de.peekandpoke.ultra.common.meta.ProcessorUtils
import de.peekandpoke.ultra.common.startsWithNone
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.VariableElement

interface CodeRenderer {
    /**
     * Returns 'true' when the renderer can handle the given VariableElement
     */
    fun canHandle(type: TypeName): Boolean

    /**
     * Renders all code-blocks for the given VariableElement
     */
    fun render(elem: VariableElement): String

    fun renderMapper(type: TypeName, depth: Int = 1): String

//    /**
//     * Renders the code-blocks by type (without variable declaration)
//     */
//    fun render(type: TypeName): String
}

/**
 * Abstract base class providing all the tools from the ProcessorUtils
 */
abstract class CodeRendererBase(
    override val logPrefix: String,
    override val processingEnv: ProcessingEnvironment
) : CodeRenderer, ProcessorUtils {

    protected val Int.asParam get() = "it$this"

    protected val Int.asOnModify get() = "on$this"

    protected fun String.indent(amount: Int, pattern: String = "    ") = lineSequence()
        .mapIndexed { idx, str -> if (idx == 0) str else str.prependIndent(pattern.repeat(amount)) }
        .joinToString(System.lineSeparator())
}

/**
 * A collection of CodeRenderers
 *
 * canHandle() returns 'true' when one of the children returns true for the given VariableElement.
 * .
 * render() returns the code by calling render on the first child that can handle the given VariableElement.
 */
class CodeRenderers(
    logPrefix: String,
    env: ProcessingEnvironment,
    private val provider: (CodeRenderers) -> List<CodeRenderer>
) : CodeRendererBase(logPrefix, env), ProcessorUtils {

    private val children by lazy { provider(this) }

    /**
     * Returns 'true' when one of the children returns true for the given VariableElement.
     */
    override fun canHandle(type: TypeName) = children.any { it.canHandle(type) }

    /**
     * Returns the code for the first matching child renderer
     */
    override fun render(elem: VariableElement) = children.first { it.canHandle(elem.asTypeName()) }.render(elem)

    override fun renderMapper(type: TypeName, depth: Int) = children.first { it.canHandle(type) }.renderMapper(type, depth)
}

/**
 * Renderer for primitive types and Strings
 */
class PrimitiveOrStringTypeCodeRenderer(logPrefix: String, env: ProcessingEnvironment) : CodeRendererBase(logPrefix, env) {

    override fun canHandle(type: TypeName) = type.isPrimitiveType || type.isStringType

    override fun render(elem: VariableElement): String {

        val cls = elem.asKotlinClassName()
        val prop = elem.simpleName

        return """
            var $prop: $cls
                get() = getResult().$prop
                set(v) = modify(getResult()::$prop, v)

            fun $prop(cb: $cls.($cls) -> $cls) = modify(getResult()::$prop, $prop.cb($prop))

        """.trimIndent()
    }

    override fun renderMapper(type: TypeName, depth: Int): String {

        return """
            ${depth.asParam}
        """.trimIndent()
    }
}

class ListAndSetCodeRenderer(
    private val root: CodeRenderers,
    logPrefix: String,
    env: ProcessingEnvironment
) : CodeRendererBase(logPrefix, env) {

    private val supported = listOf(
        "java.util.List",
        "java.util.Set"
    )

    override fun canHandle(type: TypeName) = type is ParameterizedTypeName
            // is the type supported?
            && supported.contains(type.rawType.fqn)
            // and the contained type must be supported as well
            && type.typeArguments.all { root.canHandle(it) }

    override fun render(elem: VariableElement): String {

        val prop = elem.simpleName
        val type = elem.asTypeName() as ParameterizedTypeName
        val typeParam = type.typeArguments[0]

        return """
            val $prop by lazy {
                getResult().$prop.mutator(
                    { modify(getResult()::$prop, it) },
                    { ${1.asParam}, ${1.asOnModify} ->
                        ${root.renderMapper(typeParam, 1).indent(6)}
                    }
                )
            }

        """.trimIndent()
    }

    override fun renderMapper(type: TypeName, depth: Int): String {

        val typeParam = (type as ParameterizedTypeName).typeArguments[0]
        val plus1 = depth + 1

        return """
            ${depth.asParam}.mutator(${depth.asOnModify}) { ${plus1.asParam}, ${plus1.asOnModify} ->
                ${root.renderMapper(typeParam, plus1).indent(4)}
            }
        """.trimIndent()
    }
}

class MapCodeRenderer(
    private val root: CodeRenderers,
    logPrefix: String,
    env: ProcessingEnvironment
) : CodeRendererBase(logPrefix, env) {

    private val supported = listOf(
        "java.util.Map"
    )

    override fun canHandle(type: TypeName) = type is ParameterizedTypeName
            // is the type supported ?
            && supported.contains(type.rawType.fqn)
            // The key of the map must be primitive or a string
            && type.typeArguments[0].let { it.isPrimitiveType || it.isStringType }
            // and the value part must be handled as well
            && root.canHandle(type.typeArguments[1])

    override fun render(elem: VariableElement): String {

        val prop = elem.simpleName
        val type = elem.asTypeName() as ParameterizedTypeName

        val p1 = type.typeArguments[1]

        return """
            val $prop by lazy {
                getResult().$prop.mutator(
                    { modify(getResult()::$prop, it) },
                    { ${1.asParam}, ${1.asOnModify} ->
                        ${root.renderMapper(p1, 1).indent(6)}
                    }
                )
            }

        """.trimIndent()
    }

    override fun renderMapper(type: TypeName, depth: Int): String {

        val p1 = (type as ParameterizedTypeName).typeArguments[1]

        val plus1 = depth + 1

        return """
            ${depth.asParam}.mutator(${depth.asOnModify}) { ${plus1.asParam}, ${plus1.asOnModify} ->
                ${root.renderMapper(p1, plus1).indent(4)}
            }
        """.trimIndent()
    }
}

class DataClassCodeRenderer(logPrefix: String, env: ProcessingEnvironment) : CodeRendererBase(logPrefix, env) {

    override fun canHandle(type: TypeName) = type.fqn.startsWithNone(
        "java.",                // exclude java std lib
        "javax.",               // exclude javax std lib
        "kotlin.",              // exclude kotlin std lib
        "com.google.common."    // exclude google guava
    )

    override fun render(elem: VariableElement): String {

        val prop = elem.simpleName

        return """
            val $prop by lazy { getResult().$prop.mutator { modify(getResult()::$prop, it) } }

        """.trimIndent()
    }

    override fun renderMapper(type: TypeName, depth: Int): String {

        return """
            ${depth.asParam}.mutator(${depth.asOnModify})
        """.trimIndent()
    }
}
