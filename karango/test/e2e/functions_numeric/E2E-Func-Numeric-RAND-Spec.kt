package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.RAND
import de.peekandpoke.karango.e2e.db
import io.kotlintest.matchers.doubles.shouldBeBetween
import io.kotlintest.specs.StringSpec

@Suppress("ClassName")
class `E2E-Func-Numeric-RAND-Spec` : StringSpec({

    "RAND() - direct return" {

        repeat(10) {

            val result = db.query {
                RETURN(
                    RAND()
                )
            }

            val first = result.first()
            
            first.toDouble().shouldBeBetween(0.0, 1.0, 0.0)
        }
    }
})
