import org.junit.jupiter.api.Assertions.assertEquals

class Day21Test {

    @org.junit.jupiter.api.Test
    fun testWalk() {
        val d = Day21("day21sample.txt")
        assertEquals(16, d.walk(6, false))
        assertEquals(50, d.walk(10, true))
    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day21("day21sample.txt")
        assertEquals(16, d.walk(6, false))
        assertEquals(42, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day21("day21sample.txt")

        assertEquals(528192899606863, d.runPart2())
    }

}