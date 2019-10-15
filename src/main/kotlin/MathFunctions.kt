import kotlin.math.pow
import kotlin.random.Random

class MathFunctions {

    fun add(one: Int, two: Int): Int{
        return one + two
    }

    fun sub(one: Int, two: Int): Int{
        return one - two
    }

    fun div(one: Int, two: Int): Int{
        return one / two
    }

    fun mult(one: Int, two: Int): Int {
        return one * two
    }

    fun mod(one: Int, two: Int): Int {
        return one.rem(two)
    }

    fun pow(one: Int, two: Int): Int{
        return one.toFloat().pow(two).toInt()
    }


}