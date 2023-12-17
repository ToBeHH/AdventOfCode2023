import util.Array2D
import util.Direction
import java.util.*

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day17("day17input.txt").run()
}

class Day17(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Int {
        val map = Array2D<Int>(lines[0].length, lines.size) { x, y -> lines[y][x].code - '0'.code }
        return findPath(map, 1, 3)
    }

    override fun runPart2(): Int {
        val map = Array2D<Int>(lines[0].length, lines.size) { x, y -> lines[y][x].code - '0'.code }
        return findPath(map, 4, 10)
    }

    private fun findPath(map: Array2D<Int>, minSteps: Int, maxSteps: Int): Int {
        // The key is to use a priority queue, which is essentially sorted by cost
        val queue = PriorityQueue<Head>()
        queue.add(Head(Pair(0, 0), Direction.RIGHT, 0, 1))
        queue.add(Head(Pair(0, 0), Direction.DOWN, 0, 1))
        val visited = mutableSetOf<Position>()

        while (queue.isNotEmpty()) {
            val current = queue.remove()
            if (visited.contains(current.getPosition())) {
                continue
            }
            visited.add(current.getPosition())

            // get next position
            val nextStep = current.direction.toPair()
            val nextPosition = Pair(current.position.first + nextStep.first, current.position.second + nextStep.second)
            // if outside range, skip
            if (nextPosition.first !in map.columnIndices || nextPosition.second !in map.rowIndices) {
                continue
            }
            val newCosts = current.cost + map[nextPosition]
            // if we reached the end, return the cost
            if (nextPosition == Pair(map.columns - 1, map.rows - 1)) {
                return newCosts
            }
            // now check all directions:
            for (dir in Direction.entries) {
                // if we are going back, skip
                if (dir == current.direction.opposite()) {
                    continue
                }
                if (current.stepCounter < minSteps && dir != current.direction) {
                    continue
                }
                val newStepCounter = if (dir == current.direction) {
                    current.stepCounter + 1
                } else {
                    1
                }
                if (newStepCounter > maxSteps) {
                    continue
                }
                queue.add(Head(nextPosition, dir, newCosts, newStepCounter))
            }
        }
        return -1

    }

    data class Position(val position: Pair<Int, Int>, val direction: Direction, val stepCounter: Int) {
        override fun equals(other: Any?): Boolean {
            if (other is Position) {
                return position == other.position && direction == other.direction && stepCounter == other.stepCounter
            }
            return false
        }

        override fun hashCode(): Int {
            var result = position.hashCode()
            result = 31 * result + direction.hashCode()
            result = 31 * result + stepCounter
            return result
        }
    }

    data class Head(val position: Pair<Int, Int>, val direction: Direction, val cost: Int, val stepCounter: Int) :
        Comparable<Head> {
        override fun compareTo(other: Head): Int {
            if (cost == other.cost) {
                return if (position.first == other.position.first) {
                    if (position.second == other.position.second) {
                        if (direction == other.direction) {
                            stepCounter.compareTo(other.stepCounter)
                        } else {
                            direction.compareTo(other.direction)
                        }
                    } else {
                        position.second.compareTo(other.position.second)
                    }
                } else {
                    position.first.compareTo(other.position.first)
                }
            } else {
                return cost.compareTo(other.cost)
            }
        }

        fun getPosition(): Position {
            return Position(position, direction, stepCounter)
        }
    }


}
