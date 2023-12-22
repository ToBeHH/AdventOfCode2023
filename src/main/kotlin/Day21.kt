import util.Array2D
import util.Direction
import util.Point
import kotlin.math.ceil

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day21("day21input.txt").run()
}

class Day21(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Long {
        return walk(64, false)
    }

    override fun runPart2(): Long {
        return walk(26501365, true)
    }

    internal fun walk(steps: Int, indefiniteMap: Boolean): Long {
        val startTime = System.currentTimeMillis()
        val origArrayRows = lines.size
        val origArrayWidth = lines[0].length
        var startPos = Point(0, 0)
        val map = Array2D<Char>(origArrayWidth, origArrayRows) { x, y -> if (lines[y][x] == 'S') '.' else lines[y][x] }
        for (y in lines.indices) {
            for (x in lines[y].indices) {
                if (lines[y][x] == 'S') {
                    startPos = Point(x, y)
                    break
                }
            }
        }
        if (indefiniteMap) {
            val multiplierX = ceil(steps.toDouble() / origArrayWidth.toDouble()).toInt() + 1
            val multiplierY = ceil(steps.toDouble() / origArrayRows.toDouble()).toInt() + 1
            startPos = Point(startPos.x + multiplierX * origArrayWidth, startPos.y + multiplierY * origArrayRows)
        }
        var points = mutableSetOf(startPos)
        val polynomial = mutableListOf<Int>()

        repeat(steps) {
            val newPoints = mutableSetOf<Point>()
            points.forEach { point ->
                Direction.entries.forEach { dir ->
                    val diff = dir.toPair()
                    val posX = point.x + diff.first
                    val posY = point.y + diff.second

                    if (((!indefiniteMap && posX in map.columnIndices && posY in map.rowIndices) || indefiniteMap) &&
                        map[posX % origArrayWidth, posY % origArrayRows] == '.'
                    ) {
                        newPoints.add(Point(posX, posY))
                    }
                }
            }
            points = newPoints

            // extrapolate the growth that we have
            // found best explanation here: https://www.reddit.com/r/adventofcode/comments/18nevo3/comment/kegv357/?utm_source=share&utm_medium=web2x&context=3
            // basically we are looking for a polynomial of degree 2 that fits the points we have found so far
            if (indefiniteMap && ((it - (origArrayRows / 2) + 1) % origArrayRows == 0)) {
                polynomial.add(points.size)

                if (polynomial.size == 3) {
                    val c = polynomial[0].toLong()
                    val b = (polynomial[1] - polynomial[0]).toLong()
                    val a = (polynomial[2] - polynomial[1]).toLong()
                    val x = (steps / origArrayRows).toLong()

                    println("Elapsed time: ${System.currentTimeMillis() - startTime}ms")
                    return c + b * x + (x * (x - 1) / 2) * (a - b)
                }
            }
        }
        println("Elapsed time: ${System.currentTimeMillis() - startTime}ms - size: ${points.size}")
        return points.size.toLong()
    }

}
