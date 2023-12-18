import org.junit.jupiter.api.Assertions.assertEquals
import util.Direction

class Day18Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day18("day18sample.txt")
        val map = d.buildMap {
            val lineParts = it.split(" ")
            val direction = Direction.fromChar(lineParts[0][0])
            val distance = lineParts[1].toLong()
            Pair(direction, distance)
        }

    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day18("day18sample.txt")
        assertEquals(62, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day18("day18sample.txt")

        assertEquals(952408144115L, d.runPart2())
    }

}