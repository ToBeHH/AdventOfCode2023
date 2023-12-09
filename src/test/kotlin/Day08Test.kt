import org.junit.jupiter.api.Assertions.assertEquals

class Day08Test {

    @org.junit.jupiter.api.Test
    fun testReadGame() {
        val d = Day08("day08sample1.txt")
        val network = d.readNetwork()
        assertEquals(7, network.nodes.size)
        assertEquals(
            Day08.Node("AAA", "BBB", "CCC"), network.nodes[0])
    }


    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day08("day08sample1.txt")
        assertEquals(2, d.runPart1())

        val e = Day08("day08sample2.txt")
        assertEquals(6, e.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val e = Day08("day08sample3.txt")
        assertEquals(6, e.runPart2())
    }

}