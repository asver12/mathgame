fun main(args: Array<String>){
    val steps = 10
    val gameSettings = GameSettings(
        difficulty = Difficulty.Medium,
        steps = steps,
        resultInterval = IntervalSettings(-10,10),
        calculationInterval = IntervalSettings(1,10)
    )
    var game = Game(gameSettings)
    for(i in 0..steps){
        val nextOperation = game.getNextOperation()
        println(" $nextOperation = ${game.getResult()}")
        println(" ${game.probabiltyDistribution.printDistribution()}")
    }
    println(game.getResult())
}