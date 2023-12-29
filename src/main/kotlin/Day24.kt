import util.Point3D
import util.Point3DLong


/**
 * Main method to read the file and give the result
 */
fun main() {
    Day24("day24input.txt").run()
}

class Day24(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Int {
        return countIntersects(200000000000000L, 400000000000000L)
    }

    override fun runPart2(): Long {
        // By large copied from https://github.com/ash42/adventofcode/blob/main/adventofcode2023/src/nl/michielgraat/adventofcode2023/day24/Day24.java
        //
        // We define our desired rock as X,Y,Z,VX,VY,VZ.
        // Now lets look at X.
        // If we take any hailstone x,y,z,vx,vy,vz, then (t = nanosecond) X + tVX = x + tvx.
        // We can rewrite this as t = (X-x)/(vx-VX).
        //
        // The same holds for y and z, so:
        // (X-x)/(vx-VX) = (Y-y)/(vy-VY) = (Z-z)/(vz-VZ)
        //
        // Lets rewrite that a bit:
        // (X-x)/(vx-VX) = (Y-y)/(vy-VY)
        // (X-x)(vy-VY) = (Y-y)(vx-VX)
        // Xvy - XVY - xvy + xVY = Yvx - YVX - yvx + yVX
        // YVX - XVY = -Xvy + xvy - xVY + Yvx - yvx + yVX
        //
        // YVX - XVY is the same for each hailstone (as these variables are defined by our desired position).
        //
        // Now take another hailstone x',y',z',vx',vy',vz'
        // Then:
        // YVX - XVY = -Xvy' + x'vy' - x'VY + Yvx' - y'vx' + y'VX
        //
        // And (as we said YVX - VXY is the same for all haildstones):
        //
        // -Xvy + xvy - xVY + Yvx - yvx + yVX = -Xvy' + x'vy' - x'VY + Yvx' - y'vx' + y'VX
        //
        // Or (put into order of X,Y,VX,VY):
        //
        // (vy'-vy)X + (vx-vx')Y + (y-y')VX + (x'-x)VY = - xvy + yvx + x'vy' - y'vx'
        //
        // Everything except for X,Y,VX,VY is known, so we have a linear equation with 4
        // variables. We can solve this with Gaussian elimination using a couple of
        // hailstones.
        //
        // For z:
        // (vz'-vz)X + (vx-vx')Z + (z-z')VX + (x'-x)VZ = - xvz + zvx + x'vz' - z'vx'
        //
        // Or, if you already know X and VX from part solving the equation above:
        //
        // (vx-vx')Z + (x'-x)VZ = - xvz + zvx + x'vz' - z'vx' - (vz'-vz)X - (z-z')VX

        val hailstones = lines.map { Hailstone.fromString(it) }
        var coefficients = Array(4) { DoubleArray(4) }
        var rhs = DoubleArray(4)

        // Get X,Y,VX,VY
        for (i in 0..3) {
            val h1: Hailstone = hailstones[i]
            val h2: Hailstone = hailstones[i + 1]
            coefficients[i][0] = h2.velocity.y.toDouble() - h1.velocity.y.toDouble()
            coefficients[i][1] = h1.velocity.x.toDouble() - h2.velocity.x.toDouble()
            coefficients[i][2] = h1.position.y.toDouble() - h2.position.y.toDouble()
            coefficients[i][3] = h2.position.x.toDouble() - h1.position.x.toDouble()
            rhs[i] = -h1.position.x.toDouble() * h1.velocity.y.toDouble() +
                    h1.position.y.toDouble() * h1.velocity.x.toDouble() +
                    h2.position.x.toDouble() * h2.velocity.y.toDouble() -
                    h2.position.y.toDouble() * h2.velocity.x.toDouble()
        }

        gaussianElimination(coefficients, rhs)

        val x = Math.round(rhs[0])
        val y = Math.round(rhs[1])
        val vx = Math.round(rhs[2])
        // rhs[3] is vy, but we don't need it

        // Get X,VZ
        coefficients = Array(2) { DoubleArray(2) }
        rhs = DoubleArray(2)
        for (i in 0..1) {
            val h1: Hailstone = hailstones[i]
            val h2: Hailstone = hailstones[i + 1]
            coefficients[i][0] = h1.velocity.x.toDouble() - h2.velocity.x.toDouble()
            coefficients[i][1] = h2.position.x.toDouble() - h1.position.x.toDouble()
            rhs[i] = -h1.position.x.toDouble() * h1.velocity.z.toDouble() +
                    h1.position.z.toDouble() * h1.velocity.x.toDouble() +
                    h2.position.x.toDouble() * h2.velocity.z.toDouble() -
                    h2.position.z.toDouble() * h2.velocity.x.toDouble() -
                    ((h2.velocity.z.toDouble() - h1.velocity.z.toDouble()) * x) -
                    ((h1.position.z.toDouble() - h2.position.z.toDouble()) * vx)
        }

        gaussianElimination(coefficients, rhs)

        val z = Math.round(rhs[0])
        // rhs[1] is vz, but we don't need it

        return x + y + z
    }

    internal fun countIntersects(start: Long, end: Long): Int {
        val hailstones = lines.map { Hailstone.fromString(it) }

        var count = 0
        for (i in hailstones.indices) {
            for (j in i + 1..<hailstones.size) {
                val h1 = hailstones[i]
                val h2 = hailstones[j]

                val intersection = hailstonesIntersectXY(h1, h2)

                if (intersection != null &&
                    intersection.first >= start && intersection.first <= end &&
                    intersection.second >= start && intersection.second <= end
                ) {
                    count++
                }
            }
        }

        return count
    }

    /**
     * Returns the 2D position of the intersection of the two hailstones, or null if they don't intersect
     *
     * In order to calculate, where they meet, we need to solve the following quadractic equation:
     *
     */
    internal fun hailstonesIntersectXY(h1: Hailstone, h2: Hailstone): Pair<Double, Double>? {
        val d1 = h1.velocity.y.toDouble() / h1.velocity.x.toDouble()
        val d2 = h2.velocity.y.toDouble() / h2.velocity.x.toDouble()

        val a = h1.position.y - h1.position.x * d1
        val b = h2.position.y - h2.position.x * d2

        if (d1 == d2) {
            return null
        }
        val x = (a - b) / (d2 - d1)
        val y = d1 * x + a

        // check, how much time is needed to reach this point for both hailstones
        val t1 = (x - h1.position.x) / h1.velocity.x
        val t2 = (x - h2.position.x) / h2.velocity.x
        if (t1 < 0 || t2 < 0) {
            return null
        }

        return Pair(x, y)
    }

    /**
     * Solves the given system of linear equations using Gaussian elimination
     *
     * @param coefficients the coefficients of the system of linear equations
     * @param rhs the right hand side of the system of linear equations
     */
    private fun gaussianElimination(coefficients: Array<DoubleArray>, rhs: DoubleArray) {
        val nrVariables = coefficients.size
        for (i in 0..<nrVariables) {
            // Select pivot
            val pivot = coefficients[i][i]
            // Normalize row i
            for (j in 0..<nrVariables) {
                coefficients[i][j] = coefficients[i][j] / pivot
            }
            rhs[i] = rhs[i] / pivot
            // Sweep using row i
            for (k in 0..<nrVariables) {
                if (k != i) {
                    val factor = coefficients[k][i]
                    for (j in 0..<nrVariables) {
                        coefficients[k][j] = coefficients[k][j] - factor * coefficients[i][j]
                    }
                    rhs[k] = rhs[k] - factor * rhs[i]
                }
            }
        }
    }

    class Hailstone(val position: Point3DLong, val velocity: Point3D) {

        override fun toString(): String {
            return "$position@$velocity"
        }

        companion object {
            fun fromString(line: String): Hailstone {
                val parts = line.split("@")

                return Hailstone(Point3DLong.fromString(parts[0]), Point3D.fromString(parts[1]))
            }
        }
    }

}
