import org.junit.jupiter.api.Assertions.assertEquals

class Day25Test {

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day25("day25sample.txt")
        assertEquals(56, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day25("day25sample.txt")

        assertEquals(0, d.runPart2())
    }

}