import kotlin.math.min
import kotlin.math.pow

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day04("day04input.txt").run()
}


class Day04(fileName: String) : BaseDay(fileName) {
    override fun runPart1(): Int {
        var sumPart1 = 0
        lines.forEach { line ->
            sumPart1 += getPointsForGame04(line)
        }
        return sumPart1
    }

    override fun runPart2(): Int {
        val gameDeck = mutableListOf<Game04Instance>()
        lines.forEach { line ->
            gameDeck += Game04Instance(Game04.fromString(line), 1)
        }
        for (i in gameDeck.indices) {
            playGame(gameDeck, i)
        }
        return gameDeck.sumOf { it.instances }
    }


    internal fun getPointsForGame04(line: String): Int {
        val game = Game04.fromString(line)
        val myNumbersSet = game.myNumbers.toSet()
        val winningNumbersSet = game.winningNumbers.toSet()
        val numbersInBothSets = winningNumbersSet intersect myNumbersSet
        return if (numbersInBothSets.isEmpty()) 0 else 2.0.pow(numbersInBothSets.size - 1).toInt()
    }

    internal fun playGame(gameDeck: MutableList<Game04Instance>, round: Int) {
        val game = gameDeck[round].game
        // determine number of wins
        val myNumbersSet = game.myNumbers.toSet()
        val winningNumbersSet = game.winningNumbers.toSet()
        val numbersInBothSets = winningNumbersSet intersect myNumbersSet

        val wins = numbersInBothSets.size
        for (i in round + 1..<min(gameDeck.size, round + wins + 1)) {
            gameDeck[i].instances += gameDeck[round].instances
        }
    }


    /**
     * A game for the day 04
     */
    data class Game04(val id: Int, val winningNumbers: List<Int>, val myNumbers: List<Int>) {

        companion object Factory {
            /**
             * Parse cards, which have the following format:
             * Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
             *
             * @param line the line to parse
             * @return Game object
             */
            fun fromString(line: String): Game04 {
                val parts = line.split(":")[1].split("|")
                val id = line.split(":")[0].substring(4).trim().toInt()
                val winningNumbers = parts[0].split(" ").filter { it.trim().isNotEmpty() }.map { it.toInt() }
                val myNumbers = parts[1].split(" ").filter { it.trim().isNotEmpty() }.map { it.toInt() }
                return Game04(id, winningNumbers, myNumbers)
            }
        }
    }

    data class Game04Instance(val game: Game04, var instances: Int)
}