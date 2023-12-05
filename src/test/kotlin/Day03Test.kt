import org.junit.jupiter.api.Assertions.assertEquals

class Day03Test {

    @org.junit.jupiter.api.Test
    fun testFindNumbers() {
        val d = Day03("day03input.txt")
        // extract numbers in same line
        assertEquals(listOf(617), d.findNumbers(listOf("617*......")))
        assertEquals(listOf(617, 25), d.findNumbers(listOf("617*25....")))
        assertEquals(listOf(617, 25), d.findNumbers(listOf("617*..#25..")))
        assertEquals(listOf(617), d.findNumbers(listOf("617*..#.25.")))
        // extract numbers vertically
        assertEquals(listOf(617, 25), d.findNumbers(listOf("617*......",
                                                           "..25......")))
        assertEquals(listOf(633),     d.findNumbers(listOf("..35..633.",
                                                           "......#...")))
        // extract diagonally
        assertEquals(listOf(592),     d.findNumbers(listOf(".....+.58.",
                                                           "..592.....")))
        // full test example
        assertEquals(listOf(35, 467, 633, 617, 592, 664, 598, 755),
            d.findNumbers(listOf( "467..114..",
                                "...*......",
                                "..35..633.",
                                "......#...",
                                "617*......",
                                ".....+.58.",
                                "..592.....",
                                "......755.",
                                "...\$.*....",
                                ".664.598..")))
    }

    @org.junit.jupiter.api.Test
    fun testFindGears() {
        val d = Day03("day03input.txt")
        assertEquals(listOf<Int>(), d.findGears(listOf("617*......")))
        assertEquals(listOf<Int>(), d.findGears(listOf("#34......")))
        assertEquals(listOf(10), d.findGears(listOf(".2*5.....")))
        assertEquals(listOf(10), d.findGears(listOf("2*5.....")))
        assertEquals(listOf(10), d.findGears(listOf(".......2*5")))

        // full test
        assertEquals(listOf(16345, 451490),
            d.findGears(listOf( "467..114..",
                                "...*......",
                                "..35..633.",
                                "......#...",
                                "617*......",
                                ".....+.58.",
                                "..592.....",
                                "......755.",
                                "...\$.*....",
                                ".664.598..")))
    }

    @org.junit.jupiter.api.Test
    fun testFindSymbol() {
        val d = Day03("day03input.txt")
        assertEquals(listOf(3), d.findSymbols("617*......"))
        assertEquals(listOf(0), d.findSymbols("#34......"))
        assertEquals(listOf<Int>(), d.findSymbols("....2512."))
        assertEquals(listOf(3, 5), d.findSymbols("...\$.*...."))
    }

    @org.junit.jupiter.api.Test
    fun testExtractNumber() {
        val d = Day03("day03input.txt")
        assertEquals(617, d.extractNumber(listOf("617*......"), 0, 2, Array(1) { BooleanArray(10) }))
        assertEquals(617, d.extractNumber(listOf("617*......"), 0, 0, Array(1) { BooleanArray(10) }))
    }
}