import org.junit.jupiter.api.Assertions.assertEquals

class Day20Test {
    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d1 = Day20("day20sample1.txt")
        assertEquals(32000000, d1.runPart1())

        val d2 = Day20("day20sample2.txt")
        assertEquals(11687500, d2.runPart1())
    }
}