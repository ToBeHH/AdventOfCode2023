/**
 * Main method to read the file and give the result
 */
fun main() {
    // read file sample.txt
    val lines = object {}.javaClass.getResourceAsStream("/day02input.txt")?.bufferedReader()?.readLines()
    var sumPart1 = 0
    var sumPart2 = 0
    if (lines != null) {
        for (line in lines) {
            sumPart1 += getIdOrZero(line)
            sumPart2 += getPower(line)
        }
    }
    println("Part 1: $sumPart1")
    println("Part 2: $sumPart2")
}

/**
 * Get the ID of the game or zero, if there are enough cubes of each color
 *
 * @param line the line containing a game
 * @return the ID of the game or zero
 */
fun getIdOrZero(line: String): Int {
    val game = Game.fromString(line)
    for(set in game.sets) {
        val cubes = set.getCubesByColor()
        if(cubes.getOrDefault("red", 0) > 12 ||
            cubes.getOrDefault("green", 0) > 13 ||
            cubes.getOrDefault("blue", 0) > 14) {
            return 0
        }
    }
    return game.id
}

/**
 * Get the power of a game
 *
 * The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together.
 *
 * @param line the line containing a game
 * @return the power of the game
 */
fun getPower(line: String): Int {
    val game = Game.fromString(line)
    val minimumGameSet = mutableSetOf(Cube("red", 0), Cube("blue", 0), Cube("green", 0))
    for (set in game.sets) {
        val cubes = set.getCubesByColor()
        for ((color, count) in cubes) {
            if (count > minimumGameSet.first { it.color == color }.count) {
                minimumGameSet.removeIf { it.color == color }
                minimumGameSet.add(Cube(color, count))
            }
        }
    }
    var power = 1
    for ((_, count) in minimumGameSet) {
        if (count > 0) {
            power *= count
        }
    }
    return power
}

data class Game(val id: Int, val sets: List<GameSet>) {

    /**
     * Parse a line of the game, like:
     * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
     */
    companion object Factory {
        fun fromString(line: String): Game {
            val parts = line.split(":")
            val id = parts[0].split(" ")[1].toInt()
            val sets = ArrayList<GameSet>()
            val setParts = parts[1].split(";")
            for (element in setParts) {
                sets.add(GameSet.fromString(element))
            }
            return Game(id, sets)
        }
    }
}

data class GameSet(val cubes: List<Cube>) {
    fun getCubesByColor(): Map<String, Int> {
        val cubesByColor = HashMap<String, Int>()
        for (cube in cubes) {
            val count = cubesByColor.getOrDefault(cube.color, 0)
            cubesByColor[cube.color] = count + cube.count
        }
        return cubesByColor
    }

    /**
     * Parse a line of the game, like:
     * 3 blue, 4 red
     */
    companion object Factory {
        fun fromString(line: String): GameSet {
            val cubes = ArrayList<Cube>()
            val parts = line.split(",")
            for (element in parts) {
                cubes.add(Cube.fromString(element))
            }
            return GameSet(cubes)
        }
    }
}

data class Cube(val color: String, val count: Int) {

    /**
     * Parse a line of the game, like:
     * 3 blue
     */
    companion object Factory {
        fun fromString(line: String): Cube {
            val parts = line.trim().split(" ")
            return Cube(parts[1], parts[0].toInt())
        }
    }
}