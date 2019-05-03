#ENV

Running ArangoDB in docker container (root-password:root)

```bash
docker run --name "karango" -e ARANGO_ROOT_PASSWORD= -p 8529:8529 -d arangodb 
sleep 30
docker exec -t karango arangosh --server.username=root --server.password= --javascript.execute-string="db._createDatabase('kotlindev')"

```


TODO
====

( ) Collection property configuration like indexed etc.

( ) remove dependency to me.eugeniomarletti.kotlin.processing



DONE
====

(X) Statement have NO type parameter as they return nothing

(X) figure out a solution for the problem of 'name' vs 'identifier'

(X) Property path should have a 'parent'-chain

(X) return type issues with this here:   
    
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

