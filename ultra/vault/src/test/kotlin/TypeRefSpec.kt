package de.peekandpoke.ultra.vault

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class TypeRefSpec : StringSpec({

    "KTypes must contain valid predefined types" {

        assertSoftly {
            TypeRef.Any.type.classifier shouldBe Any::class
            TypeRef.Any.type.isMarkedNullable shouldBe false
            TypeRef.AnyNull.type.classifier shouldBe Any::class
            TypeRef.AnyNull.type.isMarkedNullable shouldBe true

            TypeRef.Boolean.type.classifier shouldBe Boolean::class
            TypeRef.Boolean.type.isMarkedNullable shouldBe false
            TypeRef.BooleanNull.type.classifier shouldBe Boolean::class
            TypeRef.BooleanNull.type.isMarkedNullable shouldBe true

            TypeRef.Number.type.classifier shouldBe Number::class
            TypeRef.Number.type.isMarkedNullable shouldBe false
            TypeRef.NumberNull.type.classifier shouldBe Number::class
            TypeRef.NumberNull.type.isMarkedNullable shouldBe true

            TypeRef.String.type.classifier shouldBe String::class
            TypeRef.String.type.isMarkedNullable shouldBe false
            TypeRef.StringNull.type.classifier shouldBe String::class
            TypeRef.StringNull.type.isMarkedNullable shouldBe true
        }
    }

    "kType<String>() must work correctly" {

        val result = kType<String>()

        assertSoftly {
            result.type.classifier shouldBe String::class
            result.type.isMarkedNullable shouldBe false
        }
    }

    "kType<String?>() must work correctly" {

        val result = kType<String?>()

        assertSoftly {
            result.type.classifier shouldBe String::class
            result.type.isMarkedNullable shouldBe true
        }
    }

    "Create a type ref from a generic type directly must work" {

        val result = kType<List<String>>()

        assertSoftly {
            result.type.classifier shouldBe List::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe false
        }
    }

    "kListType<String>() must work correctly" {

        val result = kListType<String>()

        assertSoftly {
            result.type.classifier shouldBe List::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe false
        }
    }

    "kListType<String?> must work correctly" {

        val result = kListType<String?>()

        assertSoftly {
            result.type.classifier shouldBe List::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe true
        }
    }

    "Upping a String-type to a List-type must work" {

        val result = kType<String>().list

        assertSoftly {
            result.type.classifier shouldBe List::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe false
        }
    }

    "Upping a String-type to a List?-type must work" {

        val result = kType<String>().list.nullable

        assertSoftly {
            result.type.classifier shouldBe List::class
            result.type.isMarkedNullable shouldBe true
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe false
        }
    }

    "Upping a String-type to a List-type twice must work" {

        val result = kType<String>().list.list

        assertSoftly {
            result.type.classifier shouldBe List::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe List::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.arguments[0].type!!.isMarkedNullable shouldBe false
        }
    }

    "Upping a String?-type to a List-type twice must work" {

        val result = kType<String?>().list.list

        assertSoftly {
            result.type.classifier shouldBe List::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe List::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.arguments[0].type!!.isMarkedNullable shouldBe true
        }
    }

    "Upping a String?-type to a nullable List-type must work" {

        val result = kType<String?>().list.nullable

        assertSoftly {
            result.type.classifier shouldBe List::class
            result.type.isMarkedNullable shouldBe true
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe true
        }
    }

    "Downing a List<String> type must work" {

        val result = kListType<String>().unList

        assertSoftly {
            result.type.classifier shouldBe String::class
            result.type.isMarkedNullable shouldBe false
        }
    }

    "Downing a List<String?> type must work" {

        val result = kListType<String?>().unList

        assertSoftly {
            result.type.classifier shouldBe String::class
            result.type.isMarkedNullable shouldBe true
        }
    }

    "Downing a non List-Type must throw" {

        shouldThrow<IllegalStateException> {
            @Suppress("UNCHECKED_CAST")
            (kType<String>() as TypeRef<List<String>>).unList
        }
    }

    "Creating a Map<String, Int?>-type must work" {

        val result = kMapType<String, Int?>()

        assertSoftly {
            result.type.classifier shouldBe Map::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe false
            result.type.arguments[1].type!!.classifier shouldBe Int::class
            result.type.arguments[1].type!!.isMarkedNullable shouldBe true
        }
    }

    "Creating a Map<String?, Int>-type must work" {

        val result = kMapType<String?, Int>()

        assertSoftly {
            result.type.classifier shouldBe Map::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe true
            result.type.arguments[1].type!!.classifier shouldBe Int::class
            result.type.arguments[1].type!!.isMarkedNullable shouldBe false
        }
    }

    "Upping a Map<String, Int> type to a List type must work" {

        val result = kMapType<String, Int>().list

        assertSoftly {
            result.type.classifier shouldBe List::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe Map::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.arguments[0].type!!.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.arguments[1].type!!.classifier shouldBe Int::class
            result.type.arguments[0].type!!.arguments[1].type!!.isMarkedNullable shouldBe false
        }
    }

    "Downing a List<Map<String, Int>> type must work" {

        val result = kMapType<String, Int>().list.unList

        assertSoftly {
            @Suppress("RemoveExplicitTypeArguments")
            result shouldBe kMapType<String, Int>()

            result.type.classifier shouldBe Map::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe false
            result.type.arguments[1].type!!.classifier shouldBe Int::class
            result.type.arguments[1].type!!.isMarkedNullable shouldBe false
        }
    }

    "Wrapping a type with a generic class (Stored) must work" {

        val result = kType<String>().wrapWith<Stored<*>>()

        assertSoftly {
            result.type.classifier shouldBe Stored::class
            result.type.isMarkedNullable shouldBe false
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe false
        }
    }

    "Wrapping a type with a generic class (Stored?) must work" {

        val result = kType<String?>().wrapWith<Stored<*>>().nullable

        assertSoftly {
            result.type.classifier shouldBe Stored::class
            result.type.isMarkedNullable shouldBe true
            result.type.arguments[0].type!!.classifier shouldBe String::class
            result.type.arguments[0].type!!.isMarkedNullable shouldBe true
        }
    }
})
