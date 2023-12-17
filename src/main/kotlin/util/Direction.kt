package util

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

    fun opposite(): Direction {
        return when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
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