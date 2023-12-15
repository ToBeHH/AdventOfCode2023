import org.junit.jupiter.api.Assertions.assertEquals

class Day15Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day15("day15sample.txt")

        assertEquals(52, d.hashString("HASH"))
    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day15("day15sample.txt")
        assertEquals(1320, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day15("day15sample.txt")

        assertEquals(145, d.runPart2())
    }

}