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
        var start = System.currentTimeMillis()
        val result1 = runPart1()
        println("Part 1 in ${System.currentTimeMillis() - start}ms: $result1")

        start = System.currentTimeMillis()
        val result2 = runPart2()
        println("Part 2 in ${System.currentTimeMillis() - start}ms: $result2")
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