/**
 * Base class for all days
 */
abstract class BaseDay(fileName: String) {
    /**
     * The input for the day
     */
    protected var lines: List<String> =
        object {}.javaClass.getResourceAsStream("/${fileName}")?.bufferedReader()?.readLines()!!

    /**
     * Main method to read the file and give the result
     */
    fun run() {
        val result1 = runPart1()
        println("Part 1: $result1")

        val result2 = runPart2()
        println("Part 2: $result2")
    }

    /**
     * Run the part 1 of the day and return the result
     */
    abstract fun runPart1(): Any

    /**
     * Run the part 2 of the day and return the result
     */
    abstract fun runPart2(): Any


}