package de.peekandpoke.ultra.vault

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class TypeRef2Spec : StringSpec({

    "KTypes must contain valid predefined types" {

        assertSoftly {
            KTypes.Any.classifier shouldBe Any::class
            KTypes.Any.isMarkedNullable shouldBe false
            KTypes.AnyNull.classifier shouldBe Any::class
            KTypes.AnyNull.isMarkedNullable shouldBe true

            KTypes.Boolean.classifier shouldBe Boolean::class
            KTypes.Boolean.isMarkedNullable shouldBe false
            KTypes.BooleanNull.classifier shouldBe Boolean::class
            KTypes.BooleanNull.isMarkedNullable shouldBe true

            KTypes.Number.classifier shouldBe Number::class
            KTypes.Number.isMarkedNullable shouldBe false
            KTypes.NumberNull.classifier shouldBe Number::class
            KTypes.NumberNull.isMarkedNullable shouldBe true

            KTypes.String.classifier shouldBe String::class
            KTypes.String.isMarkedNullable shouldBe false
            KTypes.StringNull.classifier shouldBe String::class
            KTypes.StringNull.isMarkedNullable shouldBe true
        }
    }

    "kType<String>() must work correctly" {

        val result = kType<String>()

        assertSoftly {
            result.classifier shouldBe String::class
            result.isMarkedNullable shouldBe false
        }
    }

    "kType<String?>() must work correctly" {

        val result = kType<String?>()

        assertSoftly {
            result.classifier shouldBe String::class
            result.isMarkedNullable shouldBe true
        }
    }

    "Trying to create a generic type ref directly must throw" {

        shouldThrow<IllegalStateException> {
            kType<List<String>>()
        }
    }

    "kListType<String>() must work correctly" {

        val result = kListType<String>()

        assertSoftly {
            result.classifier shouldBe List::class
            result.isMarkedNullable shouldBe false
            result.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.isMarkedNullable shouldBe false
        }
    }

    "kListType<String?> must work correctly" {

        val result = kListType<String?>()

        assertSoftly {
            result.classifier shouldBe List::class
            result.isMarkedNullable shouldBe false
            result.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.isMarkedNullable shouldBe true
        }
    }

    "Upping a String-type to a List-type must work" {

        val result = kType<String>().upList()

        assertSoftly {
            result.classifier shouldBe List::class
            result.isMarkedNullable shouldBe false
            result.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.isMarkedNullable shouldBe false
        }
    }

    "Upping a String-type to a List-type twice must work" {

        val result = kType<String>().upList().upList()

        assertSoftly {
            result.classifier shouldBe List::class
            result.isMarkedNullable shouldBe false
            result.arguments[0].type!!.classifier shouldBe List::class
            result.arguments[0].type!!.isMarkedNullable shouldBe false
            result.arguments[0].type!!.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.arguments[0].type!!.isMarkedNullable shouldBe false
        }
    }

    "Upping a String?-type to a List-type twice must work" {

        val result = kType<String?>().upList().upList()

        assertSoftly {
            result.classifier shouldBe List::class
            result.isMarkedNullable shouldBe false
            result.arguments[0].type!!.classifier shouldBe List::class
            result.arguments[0].type!!.isMarkedNullable shouldBe false
            result.arguments[0].type!!.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.arguments[0].type!!.isMarkedNullable shouldBe true
        }
    }

    "Upping a String?-type to a nullable List-type must work" {

        val result = kType<String?>().upList(true)

        assertSoftly {
            result.classifier shouldBe List::class
            result.isMarkedNullable shouldBe true
            result.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.isMarkedNullable shouldBe true
        }
    }

    "Downing a List<String> type must work" {

        val result = kListType<String>().downList()

        assertSoftly {
            result.classifier shouldBe String::class
            result.isMarkedNullable shouldBe false
        }
    }

    "Downing a List<String?> type must work" {

        val result = kListType<String?>().downList()

        assertSoftly {
            result.classifier shouldBe String::class
            result.isMarkedNullable shouldBe true
        }
    }

    "Downing a non List-Type must throw" {

        shouldThrow<IllegalStateException> {
            kType<String>().downList()
        }
    }

    "Creating a Map<String, Int?>-type must work" {

        val result = kMapType<String, Int?>()

        assertSoftly {
            result.classifier shouldBe Map::class
            result.isMarkedNullable shouldBe false
            result.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.isMarkedNullable shouldBe false
            result.arguments[1].type!!.classifier shouldBe Int::class
            result.arguments[1].type!!.isMarkedNullable shouldBe true
        }
    }

    "Creating a Map<String?, Int>-type must work" {

        val result = kMapType<String?, Int>()

        assertSoftly {
            result.classifier shouldBe Map::class
            result.isMarkedNullable shouldBe false
            result.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.isMarkedNullable shouldBe true
            result.arguments[1].type!!.classifier shouldBe Int::class
            result.arguments[1].type!!.isMarkedNullable shouldBe false
        }
    }

    "Upping a Map<String, Int> type to a List type must work" {

        val result = kMapType<String, Int>().upList()

        assertSoftly {
            result.classifier shouldBe List::class
            result.isMarkedNullable shouldBe false
            result.arguments[0].type!!.classifier shouldBe Map::class
            result.arguments[0].type!!.isMarkedNullable shouldBe false
            result.arguments[0].type!!.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.arguments[0].type!!.isMarkedNullable shouldBe false
            result.arguments[0].type!!.arguments[1].type!!.classifier shouldBe Int::class
            result.arguments[0].type!!.arguments[1].type!!.isMarkedNullable shouldBe false
        }
    }

    "Downing a List<Map<String, Int>> type must work" {

        val result = kMapType<String, Int>().upList().downList()

        assertSoftly {
            result shouldBe kMapType<String, Int>()

            result.classifier shouldBe Map::class
            result.isMarkedNullable shouldBe false
            result.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.isMarkedNullable shouldBe false
            result.arguments[1].type!!.classifier shouldBe Int::class
            result.arguments[1].type!!.isMarkedNullable shouldBe false
        }
    }

    "Wrapping a type with a generic class (Stored) must work" {

        val result = kType<String>().wrapWith<Stored<*>>()

        assertSoftly {
            result.classifier shouldBe Stored::class
            result.isMarkedNullable shouldBe false
            result.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.isMarkedNullable shouldBe false
        }
    }

    "Wrapping a type with a generic class (Stored?) must work" {

        val result = kType<String?>().wrapWith<Stored<*>?>()

        assertSoftly {
            result.classifier shouldBe Stored::class
            result.isMarkedNullable shouldBe true
            result.arguments[0].type!!.classifier shouldBe String::class
            result.arguments[0].type!!.isMarkedNullable shouldBe true
        }
    }
})
