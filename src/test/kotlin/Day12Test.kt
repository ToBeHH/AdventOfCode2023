import org.junit.jupiter.api.Assertions.assertEquals

class Day12Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day12("day12sample.txt")
        val readRows = d.readRows(1)
        assertEquals(6, readRows.size)
        assertEquals(Day12.Row("???.###", listOf(1, 1, 3)), readRows[0])

    }

    @org.junit.jupiter.api.Test
    fun testParseLine() {
        val d = Day12("day12sample.txt")
        assertEquals(listOf(1, 1, 3), Day12.Row("#.#.###", listOf(1,1,3)).parseLine("#.#.###"))
        assertEquals(listOf(1, 1, 3), Day12.Row(".#...#....###.", listOf(1,1,3)).parseLine(".#...#....###."))
        assertEquals(listOf(1, 3, 1, 6), Day12.Row(".#.###.#.######", listOf(1, 3, 1, 6)).parseLine(".#.###.#.######"))
        assertEquals(listOf(4,1,1), Day12.Row("####.#...#...", listOf(4,1,1)).parseLine("####.#...#..."))

        val readRows = d.readRows(1)
        assertEquals(1, readRows[0].possibleArrangements())
    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day12("day12sample.txt")
        assertEquals(21, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day12("day12sample.txt")

        assertEquals(525152, d.runPart2())
    }

}