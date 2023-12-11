import org.junit.jupiter.api.Assertions.assertEquals
import util.Point

class Day11Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day11("day11sample.txt")
        val stars = d.readStars()
        d.printStars(stars)
        assertEquals(9, stars.size)
        assertEquals(Point(3, 0), stars[0])
        assertEquals(Point(7, 1), stars[1])
        assertEquals(Point(0, 2), stars[2])
        println()

        d.expandGalaxy(stars, 2)
        d.printStars(stars)
        assertEquals(Point(4, 0), stars[0])
        assertEquals(Point(9, 1), stars[1])
        assertEquals(Point(0, 2), stars[2])
        assertEquals(Point(8, 5), stars[3])

        val pairs = d.createPairs(stars)
        assertEquals(36, pairs.size)

        assertEquals(9, stars[4].distance(stars[8]))
        assertEquals(15, stars[0].distance(stars[6]))
        assertEquals(17, stars[2].distance(stars[5]))
        assertEquals(5, stars[7].distance(stars[8]))
    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day11("day11sample.txt")
        assertEquals(374, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day11("day11sample.txt")

        assertEquals(2, (2..9).count { i -> i in listOf(4, 7) })

        assertEquals(374, d.runPart2For(2))
        assertEquals(1030L, d.runPart2For(10))
        assertEquals(8410L, d.runPart2For(100))
    }

}