import org.junit.jupiter.api.Assertions.assertEquals
import util.Point3D
import util.Point3DLong

class Day24Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day24("day24sample.txt")

        assertEquals(
            Pair(14.333333333333334, 15.333333333333332),
            d.hailstonesIntersectXY(
                Day24.Hailstone(Point3DLong(19, 13, 30), Point3D(-2, 1, -2)),
                Day24.Hailstone(Point3DLong(18, 19, 22), Point3D(-1, -1, -2))
            )
        )

    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day24("day24sample.txt")
        assertEquals(2, d.countIntersects(7L, 27L))
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day24("day24sample.txt")

        assertEquals(47, d.runPart2())
    }

}