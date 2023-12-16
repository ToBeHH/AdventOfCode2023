import util.Array2D
import util.PerpetualCache
import kotlin.math.floor

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day14("day14input.txt").run()
}

class Day14(fileName: String) : BaseDay(fileName) {
    override fun runPart1(): Int {
        return calculateWeight(tiltPlatformNorth(getData()))
    }

    override fun runPart2(): Int {
        // use a cache to detect cycles
        val cache = PerpetualCache()
        var data = getData()
        val numberOfRotations = 1000000000L
        var cycle = 0L

        while (cycle++ < numberOfRotations) {
            (0..3).forEach { _ ->
                data = tiltPlatformNorth(data)
                data = data.rotateArrayClockwise()
            }

            val key = data.hashCode()
            if (cache[key] != null) {
                // cycle detected
                val cycleLen = cycle - cache[key] as Long
                // how many times we can jump this cycle?
                val numberOfJumps : Long = floor(((numberOfRotations - cycle) / cycleLen).toDouble()).toLong()
                // jump the cycle
                cycle += cycleLen * numberOfJumps
            }

            cache[key] = cycle
        }

        return calculateWeight(data)
    }

    internal fun getData(): Array2D<Char> {
        return Array2D<Char>(lines.size, lines[0].length) { x, y -> lines[x][y] }
    }

    /**
     * Move all Os to the top
     */
    fun tiltPlatformNorth(input: Array2D<Char>): Array2D<Char> {
        val result = input.clone()
        val numberOfLines = input.rows
        for (x in input.columnIndices) {
            var northernmostPoint : Int? = null
            var y  = 0
            while (y < numberOfLines) {
                when (result[y, x]) {
                    'O' -> {
                        if (northernmostPoint != null) {
                            // move rock North
                            result[northernmostPoint, x] = 'O'
                            result[y, x] = '.'
                            y = northernmostPoint
                            northernmostPoint = null
                        }
                    }
                    '.' -> {
                        if (northernmostPoint == null) {
                            northernmostPoint = y
                        }
                    }
                    '#' -> {
                        northernmostPoint = null
                    }
                }
                y++
            }
        }

        return result
    }

    fun calculateWeight(stones: Array2D<Char>): Int {
        return stones.sumOfIndexed { row, _, c ->
            if (c == 'O') {
                (stones.rows - row)
            } else {
                0
            }
        }
    }

}
