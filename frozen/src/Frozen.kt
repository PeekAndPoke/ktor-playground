package de.peekandpoke.frozen

import javassist.ClassPool
import javassist.CtNewConstructor
import javassist.util.proxy.MethodHandler
import javassist.util.proxy.ProxyFactory
import org.atteo.classindex.ClassIndex
import org.atteo.classindex.IndexSubclasses
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

@IndexSubclasses
interface MakeClassesProxiable {
    fun get(): List<String>
}

interface Frosty

object Frozen {

    fun init() {
        manipulateAllClasses()
    }

    private fun manipulateAllClasses() {

        val classes = ClassIndex.getSubclasses(MakeClassesProxiable::class.java)

        val all = classes.map { it.newInstance() }.flatMap { it.get() }.distinct()

        all.forEach { manipulateClass(it) }
    }

    private fun manipulateClass(className: String): Class<*> {

        println("manipulating $className")

        val pool = ClassPool.getDefault()

        val cls = pool.get(className)

        val defaultConstructor = CtNewConstructor.make("public ${cls.simpleName}() {}", cls)
        cls.addConstructor(defaultConstructor)

        // clear the final modifier on the class
        cls.modifiers = cls.modifiers and Modifier.FINAL.inv()

        cls.declaredFields.forEach {
            // clear the final modifier on all fields
            it.modifiers = it.modifiers and Modifier.FINAL.inv()
        }

        cls.declaredMethods.forEach {
            // clear the final modifier on all methods
            it.modifiers = it.modifiers and Modifier.FINAL.inv()
        }

        return cls.toClass()
    }

    inline fun <reified T> createLazyProxy(noinline creator: () -> T): T {

        val factory = ProxyFactory()

        factory.superclass = T::class.java

        factory.setFilter { true }

        var lazy: T? = null

        val proxy = factory.create(arrayOf(), arrayOf()) { self, thisMethod, proceed, args ->

            //            println("handling thisMethod " + thisMethod.name)
//            println("handling proceed    " + proceed.name)
//
//            args.forEachIndexed { index, any ->
//                println("arg $index : $any")
//            }

            if (lazy == null) {
                lazy = creator()

                // Copy all field values to the proxy.
                // Why? We need this to make the data class copy() method work.

                T::class.java.declaredFields.filter { !Modifier.isStatic(it.modifiers) }.forEach {
                    it.isAccessible = true
                    it.set(self, it.get(lazy))
                }
            }

            thisMethod.invoke(lazy, *args)

        } as T

        return proxy
    }

    class ProxyWrapper<T>(private val cls: Class<T>, private val original: T, private val onModify: (T) -> Unit = {}) {


        val proxy: T by lazy {
            factory.create(arrayOf(), arrayOf(), handler) as T
        }

        var result: T = original

        private var isModified = false

        private fun setValue(setter: Method, value: Any): Any? {

            createCopyOfOriginal()

            setter.isAccessible = true;

            return setter.invoke(result, value)
        }

        private fun setValue(field: Field, value: Any) {

            createCopyOfOriginal()

            field.isAccessible = true

            field.set(result, value)
        }

        private fun setValueByGetter(getter: Method, value: Any): Any? {

            val setter = findSetterForGetter(getter)

            if (setter != null) {
                return setValue(setter, value)
            }

            val field = findFieldForGetter(getter)

            if (field != null) {
                return setValue(field, value)
            }

            throw Exception("Cannot find a way to set value acquired through ${cls.canonicalName}::$getter(). No setter found and no field found")
        }

        private val handler: MethodHandler = MethodHandler { self, thisMethod, proceed, args ->

            println("thisMethod ${thisMethod.name}")
            println("proceed ${proceed.name}")

            when {
                // when it is a getter just pass it through
                // TODO: create a proxy for the value we get, when needed
                thisMethod.name.startsWith("get") -> {
                    thisMethod.invoke(result, *args).let {
                        ProxyWrapper(
                            it::class.java as Class<Any>,
                            it,
                            { changed -> setValueByGetter(thisMethod, changed) }
                        ).proxy
                    }
                }

                // all other methods are considered to change the state of the object
                else -> setValue(thisMethod, args[0])
            }
        }

        private val factory = ProxyFactory().apply { superclass = cls }

        fun String.lcFirst(): String {

            if (length == 0) return this

            return substring(0, 1).toLowerCase() + substring(1)
        }

        private fun findFieldForGetter(getter: Method) =
            cls.declaredFields.firstOrNull {
                it.name == getter.name.replaceFirst("get", "").lcFirst()
            }

        private fun findSetterForGetter(getter: Method) =
            cls.declaredMethods.firstOrNull {
                it.name == getter.name.replaceFirst("get", "set")
            }

        private fun createCopyOfOriginal() {

            if (isModified) {
                return
            }

            isModified = true

            // now we need to create a copy of the object
            result = cls.newInstance()

            cls.declaredFields.filter { !Modifier.isStatic(it.modifiers) }.forEach {
                it.isAccessible = true
                it.set(result, it.get(original))
            }

            // notify the parent that this instance has changed
            onModify(result)
        }
    }


    inline fun <reified T> melt(frosty: T, crossinline builder: (draft: T) -> Unit): T {

        val proxy = ProxyWrapper(T::class.java, frosty)

        builder(proxy.proxy)

        return proxy.result
    }
}

