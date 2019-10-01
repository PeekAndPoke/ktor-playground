package de.peekandpoke.ultra.vault

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZonedDateTime

class TypeRefSpec : StringSpec({

    "TypeRef for String must be created correctly" {

        val result = type<String>()

        result.toString() shouldBe "class java.lang.String"
    }

    "Upping a TypeRef of String  must work correctly" {

        val result = type<String>().up()

        result.toString() shouldBe "java.util.List<java.lang.String>"
    }

    "Downing a TypeRef of List<String> must work correctly" {

        val result = type<List<String>>().down<String>()

        result.toString() shouldBe "java.lang.String"
    }

    "Upping a TypeRef of Map<String, String>  must work correctly" {

        val result = type<Map<String, String>>().up()

        result.toString() shouldBe "java.util.List<java.util.Map<java.lang.String, java.lang.String>>"
    }

    "Downing a TypeRef of List<Map<String, String>> must work correctly" {

        val result = type<List<Map<String, String>>>().down<Map<String, String>>()

        result.toString() shouldBe "java.util.Map<java.lang.String, java.lang.String>"
    }

    "Downing a type with no type parameters must throw an exception" {

        shouldThrow<VaultException> {
            type<String>().down<String>()
        }
    }

    "Downing a type with more than one type parameters must throw an exception" {

        shouldThrow<VaultException> {
            type<Map<String, String>>().down<String>()
        }
    }

    "TypeRef must convert Serializable to Object" {

        val result = type<Serializable>()

        result.toString() shouldBe "class java.lang.Object"
    }

    "TypeRef must convert List<Serializable> to List<Object>" {

        val result = type<List<Serializable>>()

        result.toString() shouldBe "java.util.List<java.lang.Object>"
    }

    "TypeRef must convert List<List<Serializable>> to List<List<Object>>" {

        val result = type<List<List<Serializable>>>()

        result.toString() shouldBe "java.util.List<java.util.List<java.lang.Object>>"
    }

    "TypeRef must convert List<List<List<Serializable>>> to List<List<List<Object>>>" {

        val result = type<List<List<List<Serializable>>>>()

        result.toString() shouldBe "java.util.List<java.util.List<java.util.List<java.lang.Object>>>"
    }

    "TypeRef must convert List<List<List<List<Serializable>>>> to List<List<List<List<Object>>>>" {

        val result = type<List<List<List<List<Serializable>>>>>()

        result.toString() shouldBe "java.util.List<java.util.List<java.util.List<java.util.List<java.lang.Object>>>>"
    }

    "TypeRef must convert Map<Serializable, Serializable> to Map<Object, Object>" {

        val result = type<Map<Serializable, Serializable>>()

        result.toString() shouldBe "java.util.Map<java.lang.Object, java.lang.Object>"
    }

    "TypeRef must convert Map<Map<Serializable, Serializable>, Serializable> to Map<Map<Object, Object>, Object>" {

        val result = type<Map<Map<Serializable, Serializable>, Serializable>>()

        result.toString() shouldBe "java.util.Map<java.util.Map<java.lang.Object, java.lang.Object>, java.lang.Object>"
    }

    "TypeRef downed and then wrapped with [Stored]" {

        val result = type<List<LocalDateTime>>().down<Any>().wrapWith(Stored::class.java)

        result.toString() shouldBe "de.peekandpoke.ultra.vault.Stored<java.time.LocalDateTime>"
    }

    "TypeRef downed and then wrapped with [Stored] via inline reified wrapWith()" {

        val result = type<List<ZonedDateTime>>().down<Any>().wrapWith<Stored<*>>()

        result.toString() shouldBe "de.peekandpoke.ultra.vault.Stored<java.time.ZonedDateTime>"
    }
})
