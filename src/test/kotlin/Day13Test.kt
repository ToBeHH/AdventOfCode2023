import org.junit.jupiter.api.Assertions.assertEquals

class Day13Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day13("day13sample1.txt")
        println(d.findReflections(false))

        val d2 = Day13("day13sample2.txt")
        val blocks = d2.splitInputToBlocks(false)
        assertEquals(14, blocks[0].perfectMatchRow())
    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day13("day13sample1.txt")
        assertEquals(405, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day13("day13sample1.txt")

        assertEquals(400, d.runPart2())
    }

}