import org.junit.jupiter.api.Assertions.assertEquals

class Day01Test {

    @org.junit.jupiter.api.Test
    fun testGetInts() {
        val d = Day01("day01input.txt")
        assertEquals(12, d.getInts("1abc2"))
        assertEquals(38, d.getInts("pqr3stu8vwx"))
        assertEquals(15, d.getInts("a1b2c3d4e5f"))
        assertEquals(77, d.getInts("treb7uchet"))
    }

    @org.junit.jupiter.api.Test
    fun testGetIntsWithText() {
        val d = Day01("day01input.txt")
        assertEquals(29, d.getIntsWithText("two1nine"))
        assertEquals(83, d.getIntsWithText("eightwothree"))
        assertEquals(13, d.getIntsWithText("abcone2threexyz"))
        assertEquals(24, d.getIntsWithText("xtwone3four"))
        assertEquals(42, d.getIntsWithText("4nineeightseven2"))
        assertEquals(14, d.getIntsWithText("zoneight234"))
        assertEquals(76, d.getIntsWithText("7pqrstsixteen"))
        assertEquals(18, d.getIntsWithText("oneight"))
        assertEquals(18, d.getIntsWithText("oneightwoneight"))
        assertEquals(31, d.getIntsWithText("3two3eightjszbfourkxbh5twonepr"))
        assertEquals(24, d.getIntsWithText("xtwone3four"))
    }
}