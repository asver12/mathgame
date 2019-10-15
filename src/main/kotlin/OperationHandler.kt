class OperationHandler(val functions: FunctionHandler, val gameSettings: GameSettings) {

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
}