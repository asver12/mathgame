/**
 * Represents all settings for a game of math
 * @property difficulty Defines the difficulty of the game [Difficulty]
 * @property steps is the number of rounds played
 * @property resultInterval is the interval in which the results range
 * @property calculationInterval is the interval of numbers which are presented to the user
 *
 */
data class GameSettings(val difficulty: Difficulty, val steps: Int, val resultInterval: IntervalSettings, val calculationInterval: IntervalSettings)