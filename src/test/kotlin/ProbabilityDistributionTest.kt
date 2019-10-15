import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.log

class ProbabilityDistributionTest {
    @Test
    fun probabilityDistributionCreatsMapTest(){
        val initValue = 2048
        var probDistribution = ProbabilityDistribution(arrayListOf("Erstens", "Zweitens", "Drittens"), initValue)
        val mockedMap = hashMapOf("Erstens" to 2048, "Zweitens" to 2048, "Drittens" to 2048)
        assertEquals(mockedMap, probDistribution.probabilitys)
    }

    @Test
    fun probabilityDistributionUpdateProbabiltyTest(){
        val initValue = 2048
        var probDistribution = ProbabilityDistribution(arrayListOf("Erstens", "Zweitens", "Drittens"), initValue)
        val mockedMap = hashMapOf("Erstens" to 2048, "Zweitens" to 1024, "Drittens" to 2048)
        probDistribution.updateProbability("Zweitens")
        assertEquals(mockedMap, probDistribution.probabilitys)
    }

    @Test
    fun probabilityDistributionUpdateProbabiltysTest(){
        val initValue = 2048
        var probDistribution = ProbabilityDistribution(arrayListOf("Erstens", "Zweitens", "Drittens"), initValue)
        val mockedMap1 = hashMapOf("Erstens" to 2048, "Zweitens" to 1, "Drittens" to 2048)
        val mockedMap2 = hashMapOf("Erstens" to 16384, "Zweitens" to 4, "Drittens" to 16384)
        for(i in 0 until log(initValue.toFloat(),2.toFloat()).toInt()){
            probDistribution.updateProbability("Zweitens")
        }
        assertEquals(mockedMap1, probDistribution.probabilitys)
        probDistribution.updateProbability("Zweitens")
        assertEquals(mockedMap2, probDistribution.probabilitys)
    }

    @Test
    fun probabilityDistributioDrawRandomFunctionTest(){
        val initValue = 2048
        val functions = arrayListOf("Erstens", "Zweitens", "Drittens")
        var probDistribution = ProbabilityDistribution(functions, initValue)
        val randomFunction = probDistribution.drawRandomFunction(functions)
        assert(functions.contains(randomFunction))
        for( i in 0 .. 10){
            val randomFunction = probDistribution.drawRandomFunction(functions)
            assert(functions.contains(randomFunction))
            print(" $randomFunction \n")
        }
        for( i in 0 .. 2){
            val randomFunction = probDistribution.drawRandomFunction(functions)
            assert(functions.contains(randomFunction))
            functions.remove(randomFunction)
            print(" $randomFunction \n")
        }
    }
}