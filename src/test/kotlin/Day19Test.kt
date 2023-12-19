import org.junit.jupiter.api.Assertions.assertEquals

class Day19Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day19("day19sample.txt")

    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day19("day19sample.txt")
        assertEquals(19114, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day19("day19sample.txt")

        assertEquals(167409079868000L, d.runPart2())
    }

}