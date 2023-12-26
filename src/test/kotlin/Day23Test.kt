import org.junit.jupiter.api.Assertions.assertEquals

class Day23Test {

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day23("day23sample.txt")
        assertEquals(94, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day23("day23sample.txt")

        assertEquals(154, d.runPart2())
    }

}