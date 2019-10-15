import java.lang.Exception
import java.lang.reflect.Method

data class FunctionHandler(val reducingFunctions: ArrayList<String>, val allFunctions: ArrayList<String>) {
    var methods: Map<String, Method>

    init {
        methods =
            allFunctions.associateBy({ it }, {
                try {
                    MathFunctions::class.java.getMethod(it, Int::class.java, Int::class.java)
                } catch (e: SecurityException){
                    throw e
                } catch (e: NoSuchMethodException){
                    throw e
                } catch (e: Exception){
                    println(e)
                }
                MathFunctions::class.java.getMethod(it,Int::class.java, Int::class.java)
            })
    }
}