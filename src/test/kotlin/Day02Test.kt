import org.junit.jupiter.api.Assertions.assertEquals

class Day02Test {

    @org.junit.jupiter.api.Test
    fun testParse() {

        assertEquals(Day02.Cube("blue", 3), Day02.Cube.fromString("3 blue"))
        assertEquals(Day02.GameSet(listOf(Day02.Cube("blue", 3), Day02.Cube("red", 4))),
            Day02.GameSet.fromString("3 blue, 4 red"))

        assertEquals(Day02.Game(1, listOf(Day02.GameSet(listOf(Day02.Cube("blue", 3), Day02.Cube("red", 4))),
            Day02.GameSet(listOf(Day02.Cube("red", 1), Day02.Cube("green", 2), Day02.Cube("blue", 6))),
            Day02.GameSet(listOf(Day02.Cube("green", 2))))),
            Day02.Game.fromString("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))
    }

    @org.junit.jupiter.api.Test
    fun testGamesPart1() {
        val d = Day02("day02input.txt")
        assertEquals(1, d.getIdOrZero("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))
        assertEquals(2, d.getIdOrZero("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"))
        assertEquals(0, d.getIdOrZero("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"))
        assertEquals(0, d.getIdOrZero("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"))
        assertEquals(5, d.getIdOrZero("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"))
    }

    @org.junit.jupiter.api.Test
    fun testGetPower() {
        val d = Day02("day02input.txt")
        assertEquals(48, d.getPower("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))
        assertEquals(12, d.getPower("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"))
        assertEquals(1560, d.getPower("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"))
        assertEquals(630, d.getPower("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"))
        assertEquals(36, d.getPower("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"))
    }
}