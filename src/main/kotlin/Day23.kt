import util.Array2D
import util.Direction
import util.Point
import java.util.*
import kotlin.math.max

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day23("day23input.txt").run()
}

class Day23(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Int {
        return findLongestPath()
    }

    /**
     * Find the longest path through the maze. We need to do a complete search for all the paths possible.
     *
     * @return the number of steps
     */
    private fun findLongestPath(): Int {
        // convert input data to an array
        val map = Array2D<Char>(lines[0].length, lines.size) { x, y -> lines[y][x] }
        // start position is fixed
        val start = Pair(1, 0)
        // end position is relative to the map in the lower right corner
        val end = Pair(lines[0].length - 2, lines.size - 1)
        // the queue to store the positions to visit.
        // this is a priority queue, sorted by the longest distance
        val queue = PriorityQueue<Position>()
        queue.add(Position(start, mutableSetOf(Point(start.first, start.second)), 0))
        // the maximum number of steps
        var max = 0

        // We do a breadth first search, so we always take the first element from the queue
        // we look at all the possibilities we have
        while (queue.isNotEmpty()) {
            val pos = queue.remove()
            if (pos.pos == end) {
                // we need to look at all paths and should not stop at the first one
                if (pos.steps > max) {
                    max = pos.steps
                }
            }
            // find next positions
            pos.next(map, pos.visited, true).forEach { n ->
                queue.add(Position(n, pos.visited.toMutableSet().plus(Point(n.first, n.second)), pos.steps + 1))
            }
        }
        return max
    }

    override fun runPart2(): Int {
        // if we have a graph, the algorithm is much faster
        val nodes = convertMapToGraph()

        // traverse the graph and find the longest path between the nodes
        val start = Point(1, 0)
        val end = Point(lines[0].length - 2, lines.size - 1)

        return dfs(start, end, 0, setOf(start), nodes)
    }

    /**
     * Recursive depth first search to find the longest path between two nodes
     */
    private fun dfs(start: Point, end: Point, distance: Int, visited: Set<Point>, nodes: List<Node>): Int {
        if (start == end) {
            return distance
        }
        var max = 0
        val nextNodes =
            nodes.filter { it.start == start && !visited.contains(it.end) }.sortedBy { it.distance }.reversed()
        nextNodes.forEach { n ->
            val newVisited = visited.toMutableSet()
            newVisited.add(n.start)
            val newDistance = distance + n.distance
            max = max(max, dfs(n.end, end, newDistance, newVisited, nodes))
        }
        return max
    }


    /**
     * Convert the map to a graph, which contains all crossings and the distances between them.
     */
    private fun convertMapToGraph(): List<Node> {
        // convert input data to an array
        val map = Array2D<Char>(lines[0].length, lines.size) { x, y -> lines[y][x] }
        // start position is fixed
        val start = Pair(1, 0)
        // end position is relative to the map in the lower right corner
        val end = Pair(lines[0].length - 2, lines.size - 1)

        // a list of all corners
        val corners = detectAllCrossings(map)
        corners.add(Point(start.first, start.second))
        corners.add(Point(end.first, end.second))

        // a list of all nodes
        val nodeList = mutableListOf<Node>()
        for (i in corners.indices) {
            for (j in i + 1..<corners.size) {
                // create a new list of all corners without the two we are looking at
                val newCorners = mutableListOf<Point>()
                corners.forEachIndexed { index, point ->
                    if (index != i && index != j) {
                        newCorners.add(point)
                    }
                }
                // create a new map with all other corners replaced by forests
                val newMap = map.clone()
                newCorners.forEach { newMap[it.x, it.y] = '#' }

                val distance = findShortestPath(corners[i], corners[j], newMap)
                if (distance != null) {
                    // the nodes can be traversed in both directions
                    nodeList.add(Node(corners[i], corners[j], distance))
                    nodeList.add(Node(corners[j], corners[i], distance))
                }
            }
        }

        return nodeList
    }

    /**
     * Find all crossings in the map
     */
    private fun detectAllCrossings(map: Array2D<Char>): MutableList<Point> {
        val corners = mutableListOf<Point>()
        for (y in map.rowIndices) {
            for (x in map.columnIndices) {
                if (map[x, y] == '.') {
                    var slopes = 0
                    Direction.entries.forEach {
                        val direction = it.toPair()
                        val nextPos = Pair(x + direction.first, y + direction.second)
                        if (nextPos.first in map.columnIndices && nextPos.second in map.rowIndices &&
                            (map[nextPos] == 'v' || map[nextPos] == '<' || map[nextPos] == '>' || map[nextPos] == '^')
                        ) {
                            slopes++
                        }
                    }
                    if (slopes >= 3) {
                        corners.add(Point(x, y))
                    }
                }
            }
        }
        return corners
    }

    /**
     * Find the shortest path through the maze. We need the shortest path to calculate the distance between two nodes
     *
     * @return the number of steps
     */
    private fun findShortestPath(start: Point, end: Point, map: Array2D<Char>): Int? {
        // the queue to store the positions to visit.
        // this is a priority queue, sorted by the shortest distance
        val queue = PriorityQueue<Position>()
        queue.add(Position(Pair(start.x, start.y), emptySet(), 0))
        // the maximum number of steps
        val visited = mutableSetOf<Point>()

        // We do a breadth first search, so we always take the first element from the queue
        // we look at all the possibilities we have
        while (queue.isNotEmpty()) {
            val pos = queue.remove()
            if (pos.pos == Pair(end.x, end.y)) {
                return pos.steps
            }
            visited.add(Point(pos.pos.first, pos.pos.second))

            // find next positions
            pos.next(map, visited, false).forEach { n ->
                queue.add(Position(n, pos.visited.toMutableSet().plus(Point(n.first, n.second)), pos.steps + 1))
            }
        }
        return null
    }

    /**
     * Current position in the maze and the points, we have visited so far
     */
    data class Position(val pos: Pair<Int, Int>, val visited: Set<Point>, val steps: Int) : Comparable<Position> {
        /**
         * Compare the positions by the number of steps. This is needed for the priority queue.
         */
        override fun compareTo(other: Position): Int {
            return other.steps.compareTo(steps)
        }

        /**
         * Find the next positions we can go to
         *
         * @param map the map of the maze
         * @param avoidSlipperySlopes if true, slopes can only be passed downhill
         * @return a list of next positions
         */
        fun next(map: Array2D<Char>, visited: Set<Point>, avoidSlipperySlopes: Boolean): List<Pair<Int, Int>> {
            val next = mutableListOf<Pair<Int, Int>>()
            for (dir in Direction.entries) {
                val direction = dir.toPair()
                val nextPos = Pair(pos.first + direction.first, pos.second + direction.second)
                // still on map?
                if (nextPos.first !in map.columnIndices || nextPos.second !in map.rowIndices) {
                    continue
                }
                // will we go into the forest?
                if (map[nextPos] == '#') {
                    continue
                }
                // have we traveled there already?
                if (visited.contains(Point(nextPos.first, nextPos.second))) {
                    continue
                }
                // do we hit a slope, which we can't climb?
                if (avoidSlipperySlopes) {
                    if (dir == Direction.RIGHT && map[nextPos] == '<') {
                        continue
                    }
                    if (dir == Direction.LEFT && map[nextPos] == '>') {
                        continue
                    }
                    if (dir == Direction.UP && map[nextPos] == 'v') {
                        continue
                    }
                    if (dir == Direction.DOWN && map[nextPos] == '^') {
                        continue
                    }
                }
                next.add(nextPos)
            }
            return next
        }
    }

    data class Node(val start: Point, val end: Point, val distance: Int)

}
