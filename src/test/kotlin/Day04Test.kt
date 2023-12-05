import org.junit.jupiter.api.Assertions.assertEquals

class Day04Test {

    @org.junit.jupiter.api.Test
    fun testGetPointsForGame04() {
        // extract numbers in same line
        val d = Day04("day04input.txt")
        assertEquals(8, d.getPointsForGame04("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"))
        assertEquals(2, d.getPointsForGame04("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"))
        assertEquals(2, d.getPointsForGame04("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"))
        assertEquals(1, d.getPointsForGame04("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83"))
        assertEquals(0, d.getPointsForGame04("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36"))
        assertEquals(0, d.getPointsForGame04("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"))
    }

    @org.junit.jupiter.api.Test
    fun testPlayGame04() {
        val d = Day04("day04input.txt")
        val gameDeck = mutableListOf<Day04.Game04Instance>()
        gameDeck += Day04.Game04Instance(Day04.Game04(1, listOf(41, 48, 83, 86, 17), listOf(83, 86, 6, 31, 17, 9, 48, 53)), 1)
        gameDeck += Day04.Game04Instance(Day04.Game04(2, listOf(13, 32, 20, 16, 61), listOf(61, 30, 68, 82, 17, 32, 24, 19)), 1)
        gameDeck += Day04.Game04Instance(Day04.Game04(3, listOf(1, 21, 53, 59, 44), listOf(69, 82, 63, 72, 16, 21, 14, 1)), 1)
        gameDeck += Day04.Game04Instance(Day04.Game04(4, listOf(41, 92, 73, 84, 69), listOf(59, 84, 76, 51, 58, 5, 54, 83)), 1)
        gameDeck += Day04.Game04Instance(Day04.Game04(5, listOf(87, 83, 26, 28, 32), listOf(88, 30, 70, 12, 93, 22, 82, 36)), 1)
        gameDeck += Day04.Game04Instance(Day04.Game04(6, listOf(31, 18, 13, 56, 72), listOf(74, 77, 10, 23, 35, 67, 36, 11)), 1)

        for (i in gameDeck.indices) {
            d.playGame(gameDeck, i)
        }

        val sumPart2 = gameDeck.sumOf { it.instances }
        assertEquals(30, sumPart2)
    }

}