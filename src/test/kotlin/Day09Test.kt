import org.junit.jupiter.api.Assertions.assertEquals

class Day09Test {

    @org.junit.jupiter.api.Test
    fun testReadGame() {
        val d = Day09("day09sample.txt")
        val readings = d.readReadings()
        assertEquals(3, readings.size)
        assertEquals(
            Day09.Reading(listOf(0,3,6,9,12, 15)), readings[0])
    }

    @org.junit.jupiter.api.Test
    fun testPredictions() {
        val d = Day09("day09sample.txt")
        val readings = d.readReadings()
        assertEquals(18, readings[0].predictBack())
        assertEquals(28, readings[1].predictBack())
        assertEquals(68, readings[2].predictBack())

        assertEquals(-3, readings[0].predictFront())
        assertEquals(0, readings[1].predictFront())
        assertEquals(5, readings[2].predictFront())
    }


    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day09("day09sample.txt")
        assertEquals(114, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day09("day09sample.txt")
        assertEquals(2, d.runPart2())
    }

}