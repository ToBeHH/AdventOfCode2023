package util

import kotlin.math.abs

/**
 * Extra math functions.
 *
 * This class contains extra math functions that are not included in the
 * standard library.
 *
 * Some of the functions (gcd, lcm) are based on
 * <a href="https://github.com/ericwburden/advent_of_code_2023/blob/main/src/main/kotlin/dev/ericburden/aoc2023/Utils.kt">this</a> code.
 */
object ExtraMath {
    /**
     * Calculate the greatest common divisor between two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The greatest common divisor.
     */
    fun gcd(a: Long, b: Long): Long = if (b == 0L) abs(a) else gcd(b, a % b)

    /**
     * Calculate the least common multiple between two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The least common multiple.
     */
    fun lcm(a: Long, b: Long): Long = abs(a * b) / gcd(a, b)

    /**
     * Calculate the greatest common divisor amongst a list of numbers.
     *
     * This function extends the functionality of a [List<Long>] by adding
     * a `gcd` function that calculates the greatest common divisor amongst
     * all numbers.
     *
     * @return The greatest common divisor amongst the listed numbers.
     */
    fun List<Long>.gcd(): Long {
        require(this.isNotEmpty()) { "List must not be empty" }
        return this.reduce { acc, number -> gcd(acc, number) }
    }

    /**
     * Calculate the least common multiple amongst a list of numbers.
     *
     * This function extends the functionality of a [List<Long>] by adding
     * a `lcm` function that calculates the least common multiple amongst
     * all numbers.
     *
     * @return The least common multiple amongst the listed numbers.
     */
    fun List<Long>.lcm(): Long {
        require(this.isNotEmpty()) { "List must not be empty" }
        return this.reduce { acc, number -> lcm(acc, number) }
    }
}