package util

import kotlin.math.abs

/**
 * Represents a point in a 2D space.
 */
data class Point3D(var x: Int, var y: Int, var z: Int) {

    /**
     * Returns the Manhattan distance between this point and the other point.
     */
    fun distance(other: Point3D): Int {
        return abs(this.x - other.x) + abs(this.y - other.y) + abs(this.z - other.z)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Point3D) {
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
        fun fromString(s: String): Point3D {
            val parts = s.split(",")
            return Point3D(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
        }
    }

}
