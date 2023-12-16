import util.Array2D

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day16("day16input.txt").run()
}

class Day16(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Int {
        return followBeam(Pair(-1, 0), Direction.RIGHT)
    }

    override fun runPart2(): Int {
        val results = mutableListOf<Int>()
        // go through all possible starting positions and directions
        for (x in lines[0].indices) {
            results.add(followBeam(Pair(x, -1), Direction.DOWN))
            results.add(followBeam(Pair(x, lines.size), Direction.UP))
        }
        for (y in lines.indices) {
            results.add(followBeam(Pair(-1, y), Direction.RIGHT))
            results.add(followBeam(Pair(lines[0].length, y), Direction.LEFT))
        }
        // get the maximum value
        return results.max()
    }

    fun followBeam(startPos: Pair<Int, Int>, startDirection: Direction): Int {
        // memorize all the fields, the bean has hit
        val energized = Array2D<Energized>(lines[0].length, lines.size) { _, _ -> Energized() }
        // all the possible routes to take
        val stack = mutableListOf<Beam>()
        // start in the top left corner facing right
        stack.add(Beam(startPos, startDirection))

        while (stack.isNotEmpty()) {
            // get the first beam from the stack and move it
            val beam = stack.removeAt(0).move()
            // if beam still within the field
            if (beam.position.first >= 0 && beam.position.first < lines[0].length && beam.position.second >= 0 && beam.position.second < lines.size) {
                // if this field has already been energized by a beam in exactly this direction
                if (energized[beam.position].isEnergized(beam.direction)) {
                    // if beam is energized, we can skip it
                    continue
                }
                // if beam is not energized, we need to energize it
                energized[beam.position].energize(beam.direction)

                // move beam to the next field
                when (lines[beam.position.second][beam.position.first]) {
                    '\\' -> stack.add(Beam(beam.position, Direction.mirrorTLBR(beam.direction)))
                    '/' -> stack.add(Beam(beam.position, Direction.mirrorTRBL(beam.direction)))
                    '.' -> stack.add(beam)
                    '|' -> if (beam.direction == Direction.UP || beam.direction == Direction.DOWN) {
                        // go unchanged
                        stack.add(beam)
                    } else {
                        // split into two beams
                        stack.add(Beam(beam.position, Direction.UP))
                        stack.add(Beam(beam.position, Direction.DOWN))
                    }

                    '-' -> {
                        if (beam.direction == Direction.LEFT || beam.direction == Direction.RIGHT) {
                            // go unchanged
                            stack.add(beam)
                        } else {
                            // split into two beams
                            stack.add(Beam(beam.position, Direction.LEFT))
                            stack.add(Beam(beam.position, Direction.RIGHT))
                        }
                    }
                }
            }
        }

        return energized.sumOf { if (it.isEnergized()) 1 else 0 }
    }

    data class Beam(val position: Pair<Int, Int>, val direction: Direction) {
        /**
         * Move the beam one field in the direction it is facing
         */
        fun move(): Beam {
            val directionAsPair = direction.toPair()
            // converting to a pair makes the move operation very easy
            return Beam(
                Pair(position.first + directionAsPair.first, position.second + directionAsPair.second),
                direction
            )
        }
    }

    class Energized {
        // could also be an array, but as we only have 4 directions, this makes it easier to read
        private var energizedUp = false
        private var energizedDown = false
        private var energizedLeft = false
        private var energizedRight = false

        /**
         * Check, if this field has already been energized by a beam in exactly this direction
         */
        fun isEnergized(direction: Direction): Boolean {
            return when (direction) {
                Direction.UP -> energizedUp
                Direction.DOWN -> energizedDown
                Direction.LEFT -> energizedLeft
                Direction.RIGHT -> energizedRight
            }
        }

        /**
         * Energize this field by a beam in exactly this direction
         */
        fun energize(direction: Direction) {
            when (direction) {
                Direction.UP -> energizedUp = true
                Direction.DOWN -> energizedDown = true
                Direction.LEFT -> energizedLeft = true
                Direction.RIGHT -> energizedRight = true
            }
        }

        /**
         * Check, if this field has already been energized by a beam in any direction
         */
        fun isEnergized(): Boolean {
            return energizedUp || energizedDown || energizedLeft || energizedRight
        }
    }

    /**
     * The directions we have
     */
    enum class Direction {
        UP, DOWN, LEFT, RIGHT;

        /**
         * Convert this direction to a pair, which then makes it easier to do operations with
         */
        fun toPair(): Pair<Int, Int> {
            return when (this) {
                UP -> Pair(0, -1)
                DOWN -> Pair(0, 1)
                LEFT -> Pair(-1, 0)
                RIGHT -> Pair(1, 0)
            }
        }

        companion object {
            /**
             * Convert a pair to a direction
             */
            private fun fromPair(pair: Pair<Int, Int>): Direction {
                return when (pair) {
                    Pair(0, -1) -> UP
                    Pair(0, 1) -> DOWN
                    Pair(-1, 0) -> LEFT
                    Pair(1, 0) -> RIGHT
                    else -> throw IllegalArgumentException("Invalid pair: $pair")
                }
            }

            /**
             * Mirror the direction with a mirror going from top left to bottom right (\)
             */
            fun mirrorTLBR(direction: Direction): Direction {
                val d = direction.toPair()
                return fromPair(Pair(d.second, d.first))
            }

            /**
             * Mirror the direction with a mirror going from top right to bottom left (/)
             */
            fun mirrorTRBL(direction: Direction): Direction {
                val d = direction.toPair()
                return fromPair(Pair(-d.second, -d.first))
            }
        }
    }

}
