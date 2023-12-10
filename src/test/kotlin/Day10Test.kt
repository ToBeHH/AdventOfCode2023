import org.junit.jupiter.api.Assertions.assertEquals

class Day10Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day10("day10sample1.txt")
        d.readPipes()
        assertEquals(5, d.gridSizeX)
        assertEquals(5, d.gridSizeY)
        assertEquals(1, d.startingPoint.x)
        assertEquals(1, d.startingPoint.y)
        assertEquals(7, d.pipes.size)
    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        var d = Day10("day10sample1.txt")
        assertEquals(4, d.runPart1())

        d = Day10("day10sample2.txt")
        assertEquals(4, d.runPart1())

        d = Day10("day10sample3.txt")
        assertEquals(8, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        var d = Day10("day10sample4.txt")
        assertEquals(4, d.runPart2())

        d = Day10("day10sample5.txt")
        assertEquals(8, d.runPart2())

        d = Day10("day10sample6.txt")
        assertEquals(10, d.runPart2())
    }

}