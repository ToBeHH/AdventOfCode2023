import java.math.BigInteger
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day06("day06input.txt").run()
}

class Day06(fileName: String) : BaseDay(fileName) {
    override fun runPart1(): Long {
        return readGames().map {game -> game.numberOfRecordsBeaten()}.reduce { a,b -> a*b }
    }

    override fun runPart2(): Long {
        return readAsOneGame().numberOfRecordsBeaten()
    }

    internal fun readGames(): List<Game> {
        return Game.readGames(lines)
    }

    internal fun readAsOneGame(): Game {
        return Game.readAsOneGame(lines)
    }

    data class Game(
        val duration: BigInteger,
        val distance: BigInteger
    ) {
        /**
         * Get the number of times, the record was beaten
         */
        fun numberOfRecordsBeaten(): Long {
            val dur = duration.toDouble()
            val dis = distance.toDouble()
            // thanks to Wolfram Alpha to solve the math equation for
            // (duration - x) * x > distance
            val lowerBorder = floor(0.5 * (dur - sqrt(dur*dur - 4.0 * dis))).toLong()
            val upperBorder = ceil(0.5 * (dur + sqrt(dur*dur - 4.0 * dis))).toLong()

            return upperBorder - lowerBorder - 1
        }

        /**
         * Factory to read the game from a file
         */
        companion object Factory {
            fun readGames(lines: List<String>): List<Game> {
                val result = mutableListOf<Game>()
                val durations = lines[0].split(" ").filter { it != "" }.drop(1)
                val distances = lines[1].split(" ").filter { it != "" }.drop(1)
                assert(durations.size == distances.size)
                for (i in durations.indices) {
                    result.add(Game(durations[i].toBigInteger(), distances[i].toBigInteger()))
                }
                return result
            }

            fun readAsOneGame(lines: List<String>): Game {
                return Game(
                    lines[0].split(" ").filter { it != "" }.drop(1).reduce { a,b -> a+b }.toBigInteger(),
                    lines[1].split(" ").filter { it != "" }.drop(1).reduce { a,b -> a+b }.toBigInteger(),
                )
            }
        }
    }
}
