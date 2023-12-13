/**
 * Main method to read the file and give the result
 */
fun main() {
    Day13("day13input.txt").run()
}

class Day13(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Int {
        return findReflections(false)
    }

    override fun runPart2(): Int {
        return findReflections(true)
    }

    internal fun splitInputToBlocks(smudged: Boolean): List<Block> {
        val result = mutableListOf<Block>()
        var patternStart = 0
        // we include lines.size to make sure the last block is added
        for (a in 0..lines.size) {
            if (a == lines.size || lines[a].isEmpty()) {
                result.add(Block(lines.subList(patternStart, a), if (smudged) 1 else 0))
                patternStart = a + 1
            }
        }
        return result
    }

    internal fun findReflections(smudged: Boolean): Int {
        var sumRows = 0
        var sumCols = 0
        splitInputToBlocks(smudged).forEach { block ->
            sumRows += block.perfectMatchRow() ?: 0
            sumCols += block.perfectMatchCol() ?: 0
        }
        return sumRows * 100 + sumCols
    }

    data class Block(val rows: List<String>, val smudged: Int = 0) {
        fun perfectMatchRow(): Int? {
            return max(
                findMatchesStart(rows.size) { a, b -> rowsAreIdentical(a, b) },
                findMatchesEnd(rows.size - 1) { a, b -> rowsAreIdentical(a, b) }
            )
        }

        fun perfectMatchCol(): Int? {
            return max(
                findMatchesStart(rows[0].length) { a, b -> colsAreIdentical(a, b) },
                findMatchesEnd(rows[0].length - 1) { a, b -> colsAreIdentical(a, b) }
            )
        }

        private fun findMatchesStart(end: Int, eq: (a: Int, b: Int) -> Int): Int? {
            val row1 = 0
            for (row2 in end - 1 downTo row1 + 1) {
                if (eq(row1, row2) <= smudged) {
                    // we might have a pattern here
                    findPattern(row1, row2, eq)?.let {
                        // return, if findPattern found something, otherwise continue loop
                        return it
                    }
                }
            }
            return null
        }

        private fun findMatchesEnd(endM: Int, eq: (a: Int, b: Int) -> Int): Int? {
            for (row1 in 0..<endM) {
                if (eq(row1, endM) <= smudged) {
                    // we might have a pattern here
                    findPattern(row1, endM, eq)?.let {
                        // return, if findPattern found something, otherwise continue loop
                        return it
                    }
                }
            }
            return null
        }

        private fun findPattern(row1: Int, row2: Int, eq: (a: Int, b: Int) -> Int): Int? {
            var found = true
            var smudgeCount = 0
            // these two rows are identical. Do they form a pattern?
            for (k in 0..(row2 - row1) / 2) {
                // if `row1 + k` and `row2 - k` are identical, we have found
                // a pattern consisting of an uneven number of rows / cols
                // like ABA or ABCBA. Those patterns don't count.
                if (row1 + k == row2 - k || eq(row1 + k, row2 - k) > smudged) {
                    found = false
                    break
                }
                // we need to keep track of the number of smudges so far
                smudgeCount += eq(row1 + k, row2 - k)

                // fail fast - if we have too many smudges, we can stop
                if (smudgeCount > smudged) {
                    return null
                }
            }
            // we should have exactly `smudged` smudges (0 or 1)
            if (found && smudgeCount == smudged) {
                return row1 + (row2 - row1) / 2 + 1
            }
            return null
        }

        private fun rowsAreIdentical(i: Int, j: Int): Int {
            // return number of characters, which are different
            return rows[i].zip(rows[j]).count { it.first != it.second }
        }

        private fun colsAreIdentical(i: Int, j: Int): Int {
            // return number of characters, which are different
            return rows.indices.count { rows[it][i] != rows[it][j] }
        }

        /**
         * Return the maximum of two nullable integers
         */
        private fun max(x: Int?, y: Int?): Int? {
            if (x == null) {
                return y
            }
            if (y == null) {
                return x
            }
            return if (x > y) x else y
        }
    }
}
