import org.junit.jupiter.api.Assertions.assertEquals
import util.Direction

class Day16Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day16("day16sample.txt")
        // start process for debugging
        d.followBeam(Pair(-1, 0), Direction.RIGHT)
    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day16("day16sample.txt")
        assertEquals(46, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day16("day16sample.txt")

        assertEquals(51, d.runPart2())
    }

}