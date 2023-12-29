import util.Array2D
import util.Point3D
import kotlin.math.max
import kotlin.math.min

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day22("day22input.txt").run()
}

class Day22(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Int {
        val bricks = getBricks().sortedBy { it.corner1.z }
        val bricksAfterFall = letBricksFallDown(bricks)
        associateBricks(bricksAfterFall)
        return bricksAfterFall.count {
            //If all the bricks above are resting on at least one other brick, this brick can be disintegrated.
            if (it.bricksAbove.all { brickAbove ->
                    brickAbove.bricksBelow.any { brickBelow -> brickBelow != it }
                }) {
                return@count true
            }
            // the brick cannot be removed
            return@count false
        }
    }

    override fun runPart2(): Int {
        val bricks = getBricks().sortedBy { it.corner1.z }
        val bricksAfterFall = letBricksFallDown(bricks)
        associateBricks(bricksAfterFall)
        return bricksAfterFall.sumOf { numberOfBricksABrickDependsOn(it) }
    }

    internal fun getBricks(): List<Brick> {
        return lines.map { Brick.fromString(it) }
    }


    /**
     * Let the bricks fall down. Bricks can fall, if a z row is empty or if they don't overlap with
     * the next row.
     *
     * @param bricks the bricks to let fall down
     * @return the bricks after they fell down
     */
    internal fun letBricksFallDown(bricks: List<Brick>): List<Brick> {
        val bricksAfterGravity = mutableListOf<Brick>()

        // memorize the next possible z position for each pixel
        val ground = Array2D<Int>(bricks.maxOf { it.corner2.x } + 1, bricks.maxOf { it.corner2.y } + 1) { _, _ -> 1 }

        bricks.forEach { brick ->
            // get the maximum z for the ground
            val brickAsPixels = brick.getBlockAsPixels()
            val maximumZOnThGround = brickAsPixels.maxOf { ground[it.x, it.y] }
            // the z delta is the minimum z of the brick minus the maximum z on the ground
            val zDelta = brickAsPixels.minOf { it.z - maximumZOnThGround }
            val droppedBrick = brick.minusZ(zDelta)
            bricksAfterGravity.add(droppedBrick)

            // update the ground
            droppedBrick.getBlockAsPixels().forEach {
                ground[it.x, it.y] = it.z + 1
            }
        }
        return bricksAfterGravity
    }

    /**
     * Get the maximum z level of a list of bricks.
     *
     * @param bricks the bricks to check
     * @return the maximum z level of the bricks
     */
    internal fun getMaxZLevel(bricks: List<Brick>) =
        max(bricks.maxOf { it.corner1.z }, bricks.maxOf { it.corner2.z })


    /**
     * Associate the bricks with the bricks below and above.
     *
     * @param bricks the bricks to associate
     */
    private fun associateBricks(bricks: List<Brick>) {
        bricks.forEachIndexed { index, brick ->
            brick.id = convertNumberToLetter(index)
            brick.bricksBelow =
                bricks.filter { it.corner2.z == brick.corner1.z - 1 && it.isOverlapping(brick.minusZ(1)) }
            brick.bricksAbove =
                bricks.filter { it.corner1.z - 1 == brick.corner2.z && it.isOverlapping(brick.minusZ(-1)) }
        }
    }

    /**
     * Convert a number to a letter. 0 = A, 1 = B, 25 = Z, 26 = AA, 27 = AB, ...
     *
     * @param number the number to convert
     * @return the letter
     */
    internal fun convertNumberToLetter(number: Int): String {
        var n = number
        var result = ""
        while (n >= 0) {
            val modulo = n % 26
            result = ('A' + modulo).toString() + result
            n = (n - modulo) / 26 - 1
        }
        return result
    }

    /**
     * Get the number of bricks a brick depends on.
     *
     * @param brickToCheck the brick to check
     * @return the number of bricks the brick depends on
     */
    private fun numberOfBricksABrickDependsOn(brickToCheck: Brick): Int {
        // remember, which bricks we need to check
        val stack = mutableListOf(brickToCheck)
        // remember, which bricks have already fallen
        val fallenBricks = mutableListOf<Brick>()

        while (stack.isNotEmpty()) {
            val brick = stack.removeLast()
            if (brick in fallenBricks) continue
            fallenBricks.add(brick)

            // Now get all the bricks that will fall if `brick` is removed
            val nowFalling =
                brick.bricksAbove.filter { brickAbove ->
                    brickAbove.bricksBelow.all { it in fallenBricks }
                }

            // For each brick that is now falling, add it to the stack for
            // future consideration, unless it's already been considered.
            nowFalling.filter { it !in fallenBricks }.forEach { stack.add(it) }
        }
        // Don't forget we don't count the original brick being removed
        return fallenBricks.size - 1
    }

    class Brick(val corner1: Point3D, val corner2: Point3D) {
        var id: String = ""
        var bricksBelow: List<Brick> = listOf()
        var bricksAbove: List<Brick> = listOf()

        /**
         * Check, if this brick is on a given z level.
         *
         * @param z the z level to check
         * @return true if this brick is on the given z level, false otherwise
         */
        fun isOnZLevel(z: Int): Boolean {
            return corner1.z <= z && z <= corner2.z
        }

        /**
         * Get all pixels of this brick.
         *
         * @return a list of all pixels of this brick
         */
        fun getBlockAsPixels(): List<Point3D> {
            val pixels = mutableListOf<Point3D>()
            for (x in corner1.x..corner2.x) {
                for (y in corner1.y..corner2.y) {
                    for (z in corner1.z..corner2.z) {
                        pixels.add(Point3D(x, y, z))
                    }
                }
            }
            return pixels
        }

        /**
         * Check, if this brick is overlapping with another brick.
         *
         * @param other the other brick to check
         * @return true if this brick is overlapping with the other brick, false otherwise
         */
        fun isOverlapping(other: Brick): Boolean {
            val pixelsBlock1 = getBlockAsPixels()
            val pixelsBlock2 = other.getBlockAsPixels()
            for (pixel1 in pixelsBlock1) {
                for (pixel2 in pixelsBlock2) {
                    if (pixel1 == pixel2) {
                        return true
                    }
                }
            }
            return false
        }

        /**
         * Get a new brick with the z values decreased by the given delta.
         */
        fun minusZ(zDelta: Int): Brick {
            return Brick(
                Point3D(corner1.x, corner1.y, corner1.z - zDelta),
                Point3D(corner2.x, corner2.y, corner2.z - zDelta)
            )
        }

        override fun toString(): String {
            return "[$id] $corner1~$corner2 / below: ${bricksBelow.map { it.id }} / above: ${bricksAbove.map { it.id }}"
        }

        override fun equals(other: Any?): Boolean {
            if (other is Brick) {
                return corner1 == other.corner1 && corner2 == other.corner2
            }
            return false
        }

        override fun hashCode(): Int {
            return corner1.hashCode() + corner2.hashCode()
        }

        companion object {
            fun fromString(s: String): Brick {
                val parts = s.split("~")
                val corner1 = Point3D.fromString(parts[0])
                val corner2 = Point3D.fromString(parts[1])
                // make sure, the lower values are in corner1 and the higher values are in corner2
                return Brick(
                    Point3D(min(corner1.x, corner2.x), min(corner1.y, corner2.y), min(corner1.z, corner2.z)),
                    Point3D(max(corner1.x, corner2.x), max(corner1.y, corner2.y), max(corner1.z, corner2.z))
                )
            }
        }
    }

}
