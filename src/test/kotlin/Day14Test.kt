import org.junit.jupiter.api.Assertions.assertEquals
import util.Array2D

class Day14Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day14("day14sample.txt")

        d.tiltPlatformNorth(d.getData())
        d.calculateWeight(d.tiltPlatformNorth(d.getData()))
    }

    @org.junit.jupiter.api.Test
    fun testRotate() {
        val d = Day14("day14sample.txt")

        val arr = Array2D<Int>(3, 3, Array(3) { Array(3) { 0 } })
        arr[0, 0] = 1
        arr[0, 1] = 2
        arr[0, 2] = 3
        arr[1, 0] = 4
        arr[1, 1] = 5
        arr[1, 2] = 6
        arr[2, 0] = 7
        arr[2, 1] = 8
        arr[2, 2] = 9
        assertEquals("3 6 9\n2 5 8\n1 4 7\n", arr.rotateArrayCounterClockwise().toString())
        assertEquals("7 4 1\n8 5 2\n9 6 3\n", arr.rotateArrayClockwise().toString())
    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day14("day14sample.txt")
        assertEquals(136, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day14("day14sample.txt")

        assertEquals(64, d.runPart2())
    }

}