import kotlin.math.abs
import kotlin.math.ceil


/**
 * Main method to read the file and give the result
 */
fun main() {
    Day10("day10input.txt").run()
}

class Day10(fileName: String) : BaseDay(fileName) {

    var pipes = mutableListOf<Pipe>()

    var startingPoint = Point(-1, -1)

    var gridSizeX = 0
    var gridSizeY = 0

    override fun runPart1(): Int {
        readPipes()
        val pipes = getLoop()
        return ceil(pipes.size / 2.0).toInt()
    }

    override fun runPart2(): Int {
        readPipes()
        val loop = getLoop()
        val pipes = "|LJ"

        var count = 0

        for (y in lines.indices) {
            var inside = false
            for (x in lines.first().indices) {
                if (Point(x, y) in loop && lines[y][x] in pipes) {
                    inside = !inside
                }
                if (Point(x, y) !in loop && inside) {
                    count++
                }
            }
        }

        return count
    }

    internal fun readPipes() {
        gridSizeX = lines[0].length
        gridSizeY = lines.size

        for (y in 0..<gridSizeY) {
            for (x in 0..<gridSizeX) {
                /*
                | is a vertical pipe connecting north and south.
                - is a horizontal pipe connecting east and west.
                L is a 90-degree bend connecting north and east.
                J is a 90-degree bend connecting north and west.
                7 is a 90-degree bend connecting south and west.
                F is a 90-degree bend connecting south and east.
                 */
                when {
                    lines[y][x] == 'S' -> startingPoint = Point(x, y)

                    lines[y][x] == '|' -> pipes.add(Pipe(Point(x, y - 1), Point(x, y), Point(x, y + 1)))
                    lines[y][x] == '-' -> pipes.add(Pipe(Point(x - 1, y), Point(x, y), Point(x + 1, y)))
                    lines[y][x] == 'L' -> pipes.add(Pipe(Point(x, y - 1), Point(x, y), Point(x + 1, y)))
                    lines[y][x] == 'J' -> pipes.add(Pipe(Point(x - 1, y), Point(x, y), Point(x, y - 1)))
                    lines[y][x] == '7' -> pipes.add(Pipe(Point(x - 1, y), Point(x, y), Point(x, y + 1)))
                    lines[y][x] == 'F' -> pipes.add(Pipe(Point(x + 1, y), Point(x, y), Point(x, y + 1)))
                }
            }
        }
        assert(startingPoint.x != -1)
        assert(startingPoint.y != -1)

        eliminateUnnecessaryPipes()
    }

    private fun eliminateUnnecessaryPipes() {
        pipes = pipes.filter {
            it.from.x >= 0 && it.from.y >= 0 && it.to.x >= 0 && it.to.y >= 0
                    && it.from.x < gridSizeX && it.from.y < gridSizeY && it.to.x < gridSizeX && it.to.y < gridSizeY
        }
            .toMutableList()
    }

    private fun getLoop(): Set<Point> {
        val toVisit = ArrayDeque(listOf(startingPoint))
        val visited = mutableSetOf(startingPoint)

        while (toVisit.isNotEmpty()) {
            val current = toVisit.removeLast()
            pipes.filter { (it.from == current || it.to == current) && it.position !in visited }
                .filter { n -> current.isConnected(n.position) && n.position !in visited }
                .forEach { p ->
                    toVisit.add(p.position)
                    visited.add(p.position)

                }
        }

        return visited
    }

    data class Point(val x: Int, val y: Int) {
        override fun equals(other: Any?): Boolean {
            if (other is Point) {
                return x == other.x && y == other.y
            }
            return false
        }

        override fun hashCode(): Int {
            return x.hashCode() + y.hashCode()
        }
    }

    private fun Point.isConnected(other: Point): Boolean {
        return when {
            lines[y][x] == 'S' -> other.isConnected(this)
            lines[y][x] == '|' -> other.x == x && abs(other.y - y) == 1
            lines[y][x] == '-' -> other.y == y && abs(other.x - x) == 1
            lines[y][x] == 'L' -> (other.y == y - 1 && other.x == x) || (other.x == x + 1 && other.y == y)
            lines[y][x] == 'J' -> (other.y == y - 1 && other.x == x) || (other.x == x - 1 && other.y == y)
            lines[y][x] == '7' -> (other.y == y + 1 && other.x == x) || (other.x == x - 1 && other.y == y)
            lines[y][x] == 'F' -> (other.y == y + 1 && other.x == x) || (other.x == x + 1 && other.y == y)
            else -> false
        }
    }

    data class Pipe(val from: Point, val position: Point, val to: Point)
}
