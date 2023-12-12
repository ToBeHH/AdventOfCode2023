import util.Point

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day11("day11input.txt").run()
}

class Day11(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Int {
        val stars = readStars()
        expandGalaxy(stars, 2)
        return createPairs(stars).sumOf { it.first.distance(it.second) }
    }

    override fun runPart2(): Long {
        return runPart2For(1000000)
    }

    fun runPart2For(times: Int): Long {
        val stars = readStars()
        expandGalaxy(stars, times)
        return createPairs(stars).sumOf { it.first.distance(it.second).toLong() }
    }

    internal fun readStars(): List<Point> {
        return lines.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if (c == '#') {
                    Point(x, y)
                } else {
                    null
                }
            }
        }
    }

    internal fun expandGalaxy(stars: List<Point>, factor: Int) {
        for (y in lines.size - 1 downTo 0) {
            if (lines[y].all { it == '.' }) {
                stars.filter { it.y > y }.forEach { it.y += (factor - 1) }
            }
        }
        for (x in lines.first().length - 1 downTo 0) {
            if (lines.all { it[x] == '.' }) {
                stars.filter { it.x > x }.forEach { it.x += (factor - 1) }
            }
        }
    }

    internal fun createPairs(stars: List<Point>): List<Pair<Point, Point>> {
        return stars.mapIndexed { index, s ->
            stars.slice(index + 1..<stars.size).map { Pair(s, it) }
        }.flatten()
    }

    internal fun printStars(stars: List<Point>) {
        for (y in lines.indices) {
            for (x in lines.first().indices) {
                if (stars.contains(Point(x, y))) {
                    print('#')
                } else {
                    print('.')
                }
            }
            println()
        }
    }

}
