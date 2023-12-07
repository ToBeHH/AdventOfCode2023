import org.junit.jupiter.api.Assertions.assertEquals

class Day07Test {

    @org.junit.jupiter.api.Test
    fun testReadGame() {
        val d = Day07("day07sample.txt")
        val games = d.readGames()
        assertEquals(5, games.size)
        assertEquals(Day07.Game(listOf(Day07.Game.Card.THREE,
            Day07.Game.Card.TWO,
            Day07.Game.Card.T,Day07.Game.Card.THREE,Day07.Game.Card.K), 765), games[0])
    }

    @org.junit.jupiter.api.Test
    fun testCategorize() {
        val d = Day07("day07sample.txt")
        val games = d.readGames()
        assertEquals(Day07.Game.Category.ONE_PAIR, games[0].categorize())
        assertEquals(Day07.Game.Category.THREE_OF_A_KIND, games[1].categorize())
        assertEquals(Day07.Game.Category.TWO_PAIRS, games[2].categorize())
        assertEquals(Day07.Game.Category.TWO_PAIRS, games[3].categorize())
        assertEquals(Day07.Game.Category.THREE_OF_A_KIND, games[4].categorize())
    }

    @org.junit.jupiter.api.Test
    fun testCompare() {
        val d = Day07("day07sample.txt")
        val games = d.readGames()
        assertEquals(2, games[0].compareTo(games[1]))
        assertEquals(-1, games[1].compareTo(games[2]))
        assertEquals(-3, games[2].compareTo(games[3]))

        var sorted = games.sorted().reversed()
        assertEquals(Day07.Game(listOf(Day07.Game.Card.THREE,
            Day07.Game.Card.TWO,
            Day07.Game.Card.T,Day07.Game.Card.THREE,Day07.Game.Card.K), 765), sorted[0])
        assertEquals(Day07.Game(listOf(Day07.Game.Card.Q,
            Day07.Game.Card.Q,
            Day07.Game.Card.Q,Day07.Game.Card.J,Day07.Game.Card.A), 483), sorted[4])
    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day07("day07sample.txt")
        assertEquals(6440, d.runPart1())
    }


    @org.junit.jupiter.api.Test
    fun testCategorize2() {
        val d = Day07("day07sample.txt")
        val games = d.readGames2()
        assertEquals(Day07.Game2.Category.ONE_PAIR, games[0].categorize())
        assertEquals(Day07.Game2.Category.FOUR_OF_A_KIND, games[1].categorize())
        assertEquals(Day07.Game2.Category.TWO_PAIRS, games[2].categorize())
        assertEquals(Day07.Game2.Category.FOUR_OF_A_KIND, games[3].categorize())
        assertEquals(Day07.Game2.Category.FOUR_OF_A_KIND, games[4].categorize())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day07("day07sample.txt")
        assertEquals(5905, d.runPart2())
    }
}