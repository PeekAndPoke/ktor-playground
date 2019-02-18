TODO
====

( ) Statement have NO type parameter as they return nothing

( ) figure out a solution for the problem of 'name' vs 'identifier'
  ( ) IteratableExpression should implement a method called "getIterator() : Expression" or something

( ) Property path should have a 'parent'-chain


( ) Collection property configuration like indexed etc.

( ) return type issues with this here:   
    
    - The type of 'result' currently is: Cursor<Author>  
    - but MUST Be Cursor<List<Author>>  

    val result = db.query {
        val let = LET("let") {
            listOf(
                Author("first", "last"),
                Author("first_2", "last_2")
            )
        }

        RETURN (let)
    }
