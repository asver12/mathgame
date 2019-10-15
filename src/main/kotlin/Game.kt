import java.lang.reflect.InvocationTargetException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.random.Random

class Game(val gameSettings: GameSettings, var interimResult: Int = 0) {


    private val mathFunctions: MathFunctions = MathFunctions()
    val probabiltyDistribution: ProbabilityDistribution
    private val functionHandler: FunctionHandler

    private val opertionsLog: Queue<Triple<Int, Int, String>> = ArrayDeque()

    private var nextFunctions: Queue<Pair<Int, String>> = ArrayDeque()

    init {
        functionHandler = getFunctionsToUse()
        probabiltyDistribution = ProbabilityDistribution(functionHandler.allFunctions)
    }

    /**
     * @return interim Result
     */
    fun getResult(): Int {
        return interimResult
    }

    fun getOperationsLog(): Triple<Int, Int, String>? {
        return opertionsLog.poll()
    }

    private fun getFunctionsToUse(): FunctionHandler {
        var reducingFunctions = arrayListOf(Functions.ADD.toLowerCase, Functions.MOD.toLowerCase)
        var allFunctions = arrayListOf(Functions.SUB.toLowerCase, Functions.MULT.toLowerCase)
        if (gameSettings.difficulty == Difficulty.Medium) {
            reducingFunctions.add("div")
            allFunctions.add("pow")
        }
        allFunctions.addAll(reducingFunctions)
        return FunctionHandler(reducingFunctions, allFunctions)
    }


    /**
     * determines the next number and function to use and updates the probability function
     * @return (number-, function-) to use
     */
    fun getNextOperation(): Pair<Int, String> {
        val operationCall = getNextOperationIntern()
        callFunction(operationCall.second, operationCall.first)
        opertionsLog.add(Triple(operationCall.first, interimResult, operationCall.second))
        probabiltyDistribution.updateProbability(operationCall.second)
        return operationCall
    }

    private fun getNextOperationIntern(): Pair<Int, String> {
        var nextOperation: Pair<Int, String>
        var usedFunctions: ArrayList<String> = arrayListOf()
        // check if a function is memorized
        if (!nextFunctions.isEmpty()) return nextFunctions.poll()
        while (true) {
            // check if result is in result bounds and call fitting functions
            if (interimResult == 0) {
                nextOperation = normalOperation(arrayListOf(Functions.ADD.toLowerCase, Functions.SUB.toLowerCase))
            } else if (interimResult < gameSettings.resultInterval.lowerBound || interimResult > gameSettings.resultInterval.upperBound) {
                nextOperation = reducingOperation(functionHandler.reducingFunctions.minus(usedFunctions))
            } else {
                nextOperation = normalOperation(functionHandler.allFunctions.minus(usedFunctions))
            }
            if (nextOperation.first == 0) {
                usedFunctions.add(nextOperation.second)
            } else {
                return nextOperation
            }
        }

    }

    private fun normalOperation(functions: List<String>): Pair<Int, String> {
        val operation = selectOperation(functions)
        return operationChecks(operation)
    }

    private fun operationChecks(operation: String): Pair<Int, String> {
        if (operation == Functions.DIV.toLowerCase) {
            val divisionList = findDivisors(interimResult)
            if (divisionList.isEmpty()) {
                return Pair(0, operation)
            } else {
                return Pair(divisionList.random(), operation)
            }
        } else if (operation == Functions.POW.toLowerCase) {
            if (interimResult.absoluteValue < 2) {
                return Pair(0, operation)
            } else if (gameSettings.difficulty == Difficulty.Medium) {
                return Pair(2, operation)
            } else {
                return Pair(Random.nextInt(2, 3), operation)
            }
        } else if (operation == Functions.MOD.toLowerCase) {
            if (-3 < interimResult && interimResult < 3) {
                return Pair(0, operation)
            } else {
                return Pair(Random.nextInt(2, interimResult.absoluteValue), operation)
            }
        } else if (operation == Functions.MULT.toLowerCase) {
            if(interimResult.absoluteValue > 1){
                val upperBorder: Int = (gameSettings.resultInterval.upperBound * 1.5 / interimResult.absoluteValue).toInt()
                val lowerBorder: Int = (gameSettings.resultInterval.lowerBound * 1.5 / interimResult.absoluteValue).toInt()
                if(lowerBorder < -1 && Random.nextBoolean()){
                    return Pair(Random.nextInt(lowerBorder - 1, -2), operation)
                }
                if(upperBorder > 1){
                    return Pair(Random.nextInt(2, upperBorder + 1), operation)
                }
            }
            return Pair(0, operation)
        }
        return getPair(operation)
    }

    private fun reducingOperation(functions: List<String>): Pair<Int, String> {
        val operation = selectOperation(functions)
        if (operation == Functions.ADD.toLowerCase && interimResult.sign == 1) {
            return getPair(Functions.SUB.toLowerCase)
        }
        return operationChecks(operation)
    }

    private fun getPair(function: String): Pair<Int, String> {
        return Pair(
            Random.nextInt(
                gameSettings.calculationInterval.lowerBound,
                gameSettings.calculationInterval.upperBound
            ), function
        )
    }


    private fun selectOperation(functions: List<String>): String {
        return probabiltyDistribution.drawRandomFunction(functions)
    }

    private fun findDivisors(number: Int): ArrayList<Int> {
        val divisionList = ArrayList<Int>()
        for (i in 2 until (number / 2)) {
            if (number.rem(i) == 0) {
                divisionList.add(i)
            }
        }
        return divisionList
    }

    private fun callFunction(function: String, number: Int) {
        try {
            interimResult = functionHandler.methods[function]?.invoke(mathFunctions, interimResult, number) as Int
        } catch (e: IllegalArgumentException) {
            throw e
        } catch (e: IllegalAccessException) {
            throw e
        } catch (e: InvocationTargetException) {
            throw e
        }
    }
}