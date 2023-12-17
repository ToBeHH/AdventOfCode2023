import org.junit.jupiter.api.Assertions.assertEquals

class Day17Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day17("day17sample.txt")

    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day17("day17sample.txt")
        assertEquals(102, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day17("day17sample.txt")

        assertEquals(94, d.runPart2())
    }

}