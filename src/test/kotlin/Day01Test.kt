import org.junit.jupiter.api.Assertions.assertEquals

class Day01Test {

    @org.junit.jupiter.api.Test
    fun testGetInts() {
        assertEquals(12, getInts("1abc2"))
        assertEquals(38, getInts("pqr3stu8vwx"))
        assertEquals(15, getInts("a1b2c3d4e5f"))
        assertEquals(77, getInts("treb7uchet"))
    }

    @org.junit.jupiter.api.Test
    fun testGetIntsWithText() {
        assertEquals(29, getIntsWithText("two1nine"))
        assertEquals(83, getIntsWithText("eightwothree"))
        assertEquals(13, getIntsWithText("abcone2threexyz"))
        assertEquals(24, getIntsWithText("xtwone3four"))
        assertEquals(42, getIntsWithText("4nineeightseven2"))
        assertEquals(14, getIntsWithText("zoneight234"))
        assertEquals(76, getIntsWithText("7pqrstsixteen"))
        assertEquals(18, getIntsWithText("oneight"))
        assertEquals(18, getIntsWithText("oneightwoneight"))
        assertEquals(31, getIntsWithText("3two3eightjszbfourkxbh5twonepr"))
        assertEquals(24, getIntsWithText("xtwone3four"))
    }
}