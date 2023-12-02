import org.junit.jupiter.api.Assertions.assertEquals

class Day02Test {

    @org.junit.jupiter.api.Test
    fun testParse() {
        assertEquals(Cube("blue", 3), Cube.fromString("3 blue"))
        assertEquals(GameSet(listOf(Cube("blue", 3), Cube("red", 4))),
            GameSet.fromString("3 blue, 4 red"))

        assertEquals(Game(1, listOf(GameSet(listOf(Cube("blue", 3), Cube("red", 4))),
            GameSet(listOf(Cube("red", 1), Cube("green", 2), Cube("blue", 6))),
            GameSet(listOf(Cube("green", 2))))),
            Game.fromString("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))
    }

    @org.junit.jupiter.api.Test
    fun testGamesPart1() {
        assertEquals(1, getIdOrZero("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))
        assertEquals(2, getIdOrZero("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"))
        assertEquals(0, getIdOrZero("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"))
        assertEquals(0, getIdOrZero("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"))
        assertEquals(5, getIdOrZero("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"))
    }

    @org.junit.jupiter.api.Test
    fun testGetPower() {
        assertEquals(48, getPower("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))
        assertEquals(12, getPower("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"))
        assertEquals(1560, getPower("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"))
        assertEquals(630, getPower("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"))
        assertEquals(36, getPower("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"))
    }
}