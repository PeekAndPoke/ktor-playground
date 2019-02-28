package de.peekandpoke.karango.aql

enum class AqlFunc {
    // Overloaded functions
    LENGTH,                // TODO: impl, tests FOR Collections, Arrays
    COUNT,                 // TODO: impl, tests FOR Collections, Arrays
    REVERSE,               // TODO: impl, tests FOR Strings, Collections, Arrays

    // Array
    APPEND,                // TODO: impl, tests
    CONTAINS_ARRAY,        // TODO: impl, tests
    COUNT_DISTINCT,        // TODO: impl, tests
    COUNT_UNIQUE,          // TODO: impl, tests
    FIRST,                 // TODO: impl, tests
    FLATTEN,               // TODO: impl, tests
    INTERSECTION,          // TODO: impl, tests
    LAST,                  // TODO: impl, tests
    MINUS,                 // TODO: impl, tests
    NTH,                   // TODO: impl, tests
    OUTERSECTION,          // TODO: impl, tests
    POP,                   // TODO: impl, tests
    POSITION,              // TODO: impl, tests
    PUSH,                  // TODO: impl, tests
    REMOVE_NTH,            // TODO: impl, tests
    REMOVE_VALUE,          // TODO: impl, tests
    REMOVE_VALUES,         // TODO: impl, tests
    SHIFT,                 // TODO: impl, tests
    SLICE,                 // TODO: impl, tests
    SORTED,                // TODO: impl, tests
    SORTED_UNIQUE,         // TODO: impl, tests
    UNION,                 // TODO: impl, tests
    UNION_DISTINCT,        // TODO: impl, tests
    UNIQUE,                // TODO: impl, tests
    UNSHIFT,               // TODO: impl, tests
    
    // Date TODO
    
    
    // Database functions
    COLLECTION_COUNT,      // TODO: impl, tests
    COLLECTIONS,           // TODO: impl, tests
    DOCUMENT,              // TODO: tests
    HASH,                  // TODO: impl, tests
    APPLY,                 // TODO: impl, tests
    ASSERT,                // TODO: impl, tests
    WARN,                  // TODO: impl, tests
    CALL,                  // TODO: impl, tests
    FAIL,                  // TODO: impl, tests
    NOOPT,                 // TODO: impl, tests
    PASSTHRU,              // TODO: impl, tests
    SLEEP,                 // TODO: impl, tests
    V8,                    // TODO: impl, tests
    VERSION,               // TODO: impl, tests

    // Document / Object TODO

    // Fulltext TODO
    
    // Geo // TODO
    
    

    // Miscellaneous
    FIRST_LIST,            // TODO: impl, tests
    FIRST_DOCUMENT,        // TODO: impl, tests
    NOT,                   // TODO: tests
    NOT_NULL,              // TODO: impl, tests
    
    // Numeric
    ABS,                   // TODO: impl, tests
    ACOS,                  // TODO: impl, tests
    ASIN,                  // TODO: impl, tests
    ATAN,                  // TODO: impl, tests
    ATAN2,                 // TODO: impl, tests
    AVERAGE,               // TODO: impl, tests
    AVG,                   // TODO: impl, tests
    CEIL,                  // TODO: impl, tests
    COS,                   // TODO: impl, tests
    DEGREES,               // TODO: impl, tests
    EXP,                   // TODO: impl, tests
    EXP2,                  // TODO: impl, tests
    FLOOR,                 // TODO: impl, tests
    LOG,                   // TODO: impl, tests
    LOG2,                  // TODO: impl, tests
    LOG10,                 // TODO: impl, tests
    MAX,                   // TODO: impl, tests
    MEDIAN,                // TODO: impl, tests
    MIN,                   // TODO: impl, tests
    PERCENTILE,            // TODO: impl, tests
    PI,                    // TODO: impl, tests
    POW,                   // TODO: impl, tests
    RADIANS,               // TODO: impl, tests
    RAND,                  // TODO: impl, tests
    RANGE,                 // TODO: impl, tests
    ROUND,                 // TODO: impl, tests
    SIN,                   // TODO: impl, tests
    SQRT,                  // TODO: impl, tests
    STDDEV_POPULATION,     // TODO: impl, tests
    STDDEV_SAMPLE,         // TODO: impl, tests
    STDDEV,                // TODO: impl, tests
    SUM,                   // TODO: impl, tests
    TAN,                   // TODO: impl, tests
    VARIANCE_POPULATION,   // TODO: impl, tests
    VARIANCE_SAMPLE,       // TODO: impl, tests
    VARIANCE,              // TODO: impl, tests       
    
    // Strings
    CHAR_LENGTH,           
    CONCAT,                
    CONCAT_SEPARATOR,      
    CONTAINS,              
    ENCODE_URI_COMPONENT,  // TODO: impl, tests
    FIND_FIRST,            
    FIND_LAST,             
    JSON_PARSE,            
    JSON_STRINGIFY,        
    LEFT,                  
    LEVENSHTEIN_DISTANCE,  
    LIKE,                  // TODO: impl, tests
    LOWER,                 
    LTRIM,                 
    MD5,                   // TODO: impl, tests
    RANDOM_TOKEN,          // TODO: impl, tests
    REGEX_MATCHES,         // TODO: impl, tests
    REGEX_SPLIT,           // TODO: impl, tests
    REGEX_TEST,            // TODO: impl, tests
    REGEX_REPLACE,         // TODO: impl, tests
    RIGHT,                 
    RTRIM,                 
    SHA1,                  
    SHA512,                
    SPLIT,                 
    SOUNDEX,               
    SUBSTITUTE,            // TODO: impl, tests
    SUBSTRING,             // TODO: impl, tests
    TOKENS,                // TODO: impl, tests
    TO_BASE64,             
    TO_HEX,                
    TRIM,                  
    UPPER,                 
    UUID,                  

    // Type checks
    IS_NULL,            // TODO: impl, tests
    IS_BOOL,            // TODO: impl, tests
    IS_NUMBER,          // TODO: impl, tests
    IS_STRING,          // TODO: impl, tests
    IS_ARRAY,           // TODO: impl, tests
    IS_LIST,            // TODO: impl, tests
    IS_OBJECT,          // TODO: impl, tests
    IS_DOCUMENT,        // TODO: impl, tests
    IS_DATESTRING,      // TODO: impl, tests
    IS_KEY,             // TODO: impl, tests
    TYPENAME,           // TODO: impl, tests
    
    // Type Conversion
    TO_BOOL,
    TO_NUMBER,
    TO_STRING,
    TO_ARRAY,
    TO_LIST,
}

fun <T> AqlFunc.call(type: TypeRef<T>, vararg args: Expression<*>) = FuncCall.of(type, this, args)

fun AqlFunc.anyCall(vararg args: Expression<*>) = FuncCall.any(this, args)

fun <T> AqlFunc.arrayCall(type: TypeRef<List<T>>, vararg args: Expression<*>) = FuncCall.array(this, type, args)

fun AqlFunc.boolCall(vararg args: Expression<*>) = FuncCall.bool(this, args)

fun AqlFunc.numberCall(vararg args: Expression<*>) = FuncCall.number(this, args)

fun AqlFunc.stringCall(vararg args: Expression<*>) = FuncCall.string(this, args)

interface FuncCall<T> : Expression<T> {

    companion object {

        fun <X> of(type: TypeRef<X>, func: AqlFunc, args: Array<out Expression<*>>): Expression<X> = FuncCallImpl(type, func, args)

        fun any(func: AqlFunc, args: Array<out Expression<*>>): Expression<Any> = FuncCallImpl(TypeRef.Any, func, args)

        fun <T> array(func: AqlFunc, type: TypeRef<List<T>>, args: Array<out Expression<*>>): FuncCall<List<T>> = FuncCallImpl(type, func, args)
        
        fun bool(func: AqlFunc, args: Array<out Expression<*>>): Expression<Boolean> = FuncCallImpl(TypeRef.Boolean, func, args)
        
        fun number(func: AqlFunc, args: Array<out Expression<*>>): Expression<Number> = FuncCallImpl(TypeRef.Number, func, args)
        
        fun string(func: AqlFunc, args: Array<out Expression<*>>): Expression<String> = FuncCallImpl(TypeRef.String, func, args)
    }
}

internal class FuncCallImpl<T>(private val type: TypeRef<T>, private val func: AqlFunc, private val args: Array<out Expression<*>>) : FuncCall<T> {

    override fun getType() = type
    override fun printAql(p: AqlPrinter) = p.append("${func.name}(").join(args).append(")")
}

