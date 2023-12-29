package util

import kotlin.math.abs

/**
 * Represents a point in a 2D space.
 */
data class Point3DLong(var x: Long, var y: Long, var z: Long) {

    /**
     * Returns the Manhattan distance between this point and the other point.
     */
    fun distance(other: Point3DLong): Long {
        return abs(this.x - other.x) + abs(this.y - other.y) + abs(this.z - other.z)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Point3DLong) {
            return x == other.x && y == other.y && z == other.z
        }
        return false
    }

    override fun hashCode(): Int {
        return x.hashCode() + y.hashCode() + z.hashCode()
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    companion object {
        fun fromString(s: String): Point3DLong {
            val parts = s.split(",")
            return Point3DLong(parts[0].trim().toLong(), parts[1].trim().toLong(), parts[2].trim().toLong())
        }
    }

}
