import org.junit.jupiter.api.Assertions.assertEquals

class Day03Test {

    @org.junit.jupiter.api.Test
    fun testFindNumbers() {
        // extract numbers in same line
        assertEquals(listOf(617), findNumbers(listOf("617*......")))
        assertEquals(listOf(617, 25), findNumbers(listOf("617*25....")))
        assertEquals(listOf(617, 25), findNumbers(listOf("617*..#25..")))
        assertEquals(listOf(617), findNumbers(listOf("617*..#.25.")))
        // extract numbers vertically
        assertEquals(listOf(617, 25), findNumbers(listOf("617*......",
                                                         "..25......")))
        assertEquals(listOf(633),     findNumbers(listOf("..35..633.",
                                                         "......#...")))
        // extract diagonally
        assertEquals(listOf(592),     findNumbers(listOf(".....+.58.",
                                                         "..592.....")))
        // full test example
        assertEquals(listOf(35, 467, 633, 617, 592, 664, 598, 755),
            findNumbers(listOf( "467..114..",
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
        assertEquals(listOf<Int>(), findGears(listOf("617*......")))
        assertEquals(listOf<Int>(), findGears(listOf("#34......")))
        assertEquals(listOf(10), findGears(listOf(".2*5.....")))
        assertEquals(listOf(10), findGears(listOf("2*5.....")))
        assertEquals(listOf(10), findGears(listOf(".......2*5")))

        // full test
        assertEquals(listOf(16345, 451490),
            findGears(listOf(   "467..114..",
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
        assertEquals(listOf(3), findSymbols("617*......"))
        assertEquals(listOf(0), findSymbols("#34......"))
        assertEquals(listOf<Int>(), findSymbols("....2512."))
        assertEquals(listOf(3, 5), findSymbols("...\$.*...."))
    }

    @org.junit.jupiter.api.Test
    fun testExtratNumber() {
        assertEquals(617, extractNumber(listOf("617*......"), 0, 2, Array(1) { BooleanArray(10) }))
        assertEquals(617, extractNumber(listOf("617*......"), 0, 0, Array(1) { BooleanArray(10) }))
    }
}