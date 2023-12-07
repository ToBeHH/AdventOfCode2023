import org.junit.jupiter.api.Assertions.assertEquals

class Day06Test {

    @org.junit.jupiter.api.Test
    fun testReadGame() {
        // extract numbers in same line
        val d = Day06("day06sample.txt")
        val games = d.readGames()
        assertEquals(3, games.size)
        assertEquals(7.toBigInteger(), games[0].duration)
        assertEquals(9.toBigInteger(), games[0].distance)
    }

    @org.junit.jupiter.api.Test
    fun testCalculateAllDistances() {
        val d = Day06("day06sample.txt")
        val games = d.readGames()
        assertEquals(4, games[0].numberOfRecordsBeaten())
        assertEquals(288, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day06("day06sample.txt")
        val game = d.readAsOneGame()
        assertEquals(Day06.Game(71530.toBigInteger(), 940200.toBigInteger()), game)
        assertEquals(71503, d.runPart2())
    }
}