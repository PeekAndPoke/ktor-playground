package de.peekandpoke.karango.aql

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.io.Serializable

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

    "TypeRef must convert List<List<List<Serializable>>> to List<List<Object>>" {

        val result = type<List<List<List<Serializable>>>>()

        result.toString() shouldBe "java.util.List<java.util.List<java.util.List<java.lang.Object>>>"
    }
})
