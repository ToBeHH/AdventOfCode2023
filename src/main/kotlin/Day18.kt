import util.Direction
import kotlin.math.abs

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day18("day18input.txt").run()
}

class Day18(fileName: String) : BaseDay(fileName) {
    override fun runPart1(): Long {
        // we basically build the same map twice, but the input is different
        // therefore we need to have the parcing logic in a separate function
        val map = buildMap {
            val lineParts = it.split(" ")
            val direction = Direction.fromChar(lineParts[0][0])
            val distance = lineParts[1].toLong()
            Pair(direction, distance)
        }
        return map
    }

    override fun runPart2(): Long {
        // we will use the same map as in part 1, but we need to change the parsing logic
        val map = buildMap {
            val lineParts = it.split(" ")
            val direction = when (lineParts[2][7]) {
                '0' -> Direction.RIGHT
                '1' -> Direction.DOWN
                '2' -> Direction.LEFT
                '3' -> Direction.UP
                else -> throw IllegalArgumentException("Invalid direction: ${lineParts[2][6]}")
            }
            val distance = lineParts[2].substring(2, 7).toLong(radix = 16)
            Pair(direction, distance)
        }
        return map
    }

    internal fun buildMap(parse: (line: String) -> Pair<Direction, Long>): Long {
        // we will do three things in 1 pass:
        // memorize all the corners - because we need them to calculate the area
        // count the number of left and right turns - because we need to know which direction to fill
        // calculate the length of the border - because we need to add it to the area later
        var countLefts = 0
        var countRights = 0
        var position = Pair(0L, 0L) // the current position
        var previousDirection: Direction? = null
        val corners = mutableListOf<Pair<Long, Long>>()
        var borderLength = 0L
        for (line in lines) {
            val (dir, distance) = parse(line)
            if (previousDirection != null) {
                if (previousDirection.turnLeft() == dir) countLefts++
                if (previousDirection.turnRight() == dir) countRights++
            }
            previousDirection = dir
            val direction = dir.toPair()
            position = Pair(position.first + direction.first * distance, position.second + direction.second * distance)
            corners.add(position)
            borderLength += distance
        }

        // fill to the right or fill on the left?
        val fillRight = countRights > countLefts
        // the shoelace-formula needs the corners to be in clockwise order
        // if we need to fill to the right, we need to reverse the order
        if (fillRight) {
            corners.reverse()
        }

        // applying shoelace-formula to find out the area covered by the corners
        var sum = 0L
        for (i in 0..<corners.size - 1) {
            sum += (corners[i].first * corners[i + 1].second - corners[i].second * corners[i + 1].first)
        }
        // last corner
        sum += (corners[corners.size - 1].first * corners[0].second - corners[corners.size - 1].second * corners[0].first)

        // need to add the border to the area because they are covered as well
        return (abs(sum) + borderLength) / 2L + 1L
    }
}
