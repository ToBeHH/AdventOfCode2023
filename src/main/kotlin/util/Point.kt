package util

import kotlin.math.abs

/**
 * Represents a point in a 2D space.
 */
data class Point(var x: Int, var y: Int) {

    /**
     * Returns the Manhattan distance between this point and the other point.
     */
    fun distance(other: Point): Int {
        return abs(this.x - other.x) + abs(this.y - other.y)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Point) {
            return x == other.x && y == other.y
        }
        return false
    }

    override fun hashCode(): Int {
        return x.hashCode() + y.hashCode()
    }

    override fun toString(): String {
        return "($x, $y)"
    }

}
