import java.lang.Exception
import kotlin.random.Random

class ProbabilityDistribution(functions: List<String>, initValue: Int = 2048) {
    val TAG: String = "ProbabilityDistribution"

    var probabilitys: HashMap<String, Int> = HashMap()

    init {
        functions.associateTo(probabilitys) { it to initValue }
    }

    fun updateProbability(function: String) {
        contains(function)
        if (probabilitys[function] == 1) {
            updateProbabilitys()
        }
        probabilitys[function] = probabilitys.getValue(function) / 2

    }

    private fun sumFunctions(functions: List<String>): Int {
        return functions.sumBy { try {
            probabilitys.getValue(it)
        } catch (e: Exception){
            print(it)
            throw e
        }}
    }

    fun drawRandomFunction(functions: List<String>): String {
        val totalSum = sumFunctions(functions)
        val position = Random.nextInt(0, totalSum)
        var func = ""
        var sum = 0
        for (i in functions) {
            contains(i)
            if (sum + probabilitys.getValue(i) > position) {
                func = i
                break
            }
            sum += probabilitys.getValue(i)
        }
        return func
    }

    private fun contains(function: String) {
        if (!probabilitys.containsKey(function)) {
            throw Exception(" $TAG: $function doesnt exist in map")
        }
    }


    fun printDistribution(): String {
        var probString = "Distribution: \n"
        probabilitys.forEach { k, v -> probString += "$k : $v \n" }
        return probString
    }

    private fun updateProbabilitys() {
        probabilitys.forEach { k, v -> probabilitys[k] = v * 8 }
        print(printDistribution())
    }

}