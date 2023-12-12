import util.Cache
import util.PerpetualCache

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day12("day12input.txt").run()
}

class Day12(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Long {
        return readRows(1).sumOf(Row::countPossibleArrangements)
    }

    override fun runPart2(): Long {
        return readRows(5).sumOf(Row::countPossibleArrangements)
    }

    internal fun readRows(fold: Int): List<Row> {
        return lines.map { Row.read(it, fold) }
    }

    data class Row(val row: String, val possibleArrangements: List<Int>) {
        // storing everything in a cache to speed up the process
        // actually this is quite vital as the app would otherwise run for hours
        var cache: Cache = PerpetualCache()

        fun countPossibleArrangements(): Long {
            // re-init cache
            cache.clear()
            // adding the extra . for better EOL handling
            return numSolutions("$row.", possibleArrangements)
        }

        /**
         * Recursive function to count the number of solutions
         *
         * With a lot of help from https://github.com/fuglede/adventofcode/blob/fdd8d90dd4bf4bd6312b53028972479827c07207/2023/day12/solutions.py#L8
         */
        private fun numSolutions(s: String, sizes: List<Int>, numDoneInGroup: Int = 0): Long {
            val key = "$s-$sizes-$numDoneInGroup"

            if (cache[key] != null) {
                return cache[key] as Long
            }

            if (s.isEmpty()) {
                val ret = if (sizes.isEmpty() && numDoneInGroup == 0) 1L else 0L
                cache.set(key, ret)
                return ret
            }
            var numSols = 0L
            // If next letter is a "?", we branch
            val possible = if (s[0] == '?') listOf('.', '#') else listOf(s[0])
            for (c in possible) {
                if (c == '#') {
                    // Extend current group
                    numSols += numSolutions(s.substring(1), sizes, numDoneInGroup + 1)
                } else {
                    if (numDoneInGroup > 0) {
                        if (sizes.isNotEmpty() && sizes[0] == numDoneInGroup) {
                            // If we were in a group that can be closed, close it
                            numSols += numSolutions(s.substring(1), sizes.subList(1, sizes.size))
                        }
                    } else {
                        // If we are not in a group, move on to next symbol
                        numSols += numSolutions(s.substring(1), sizes)
                    }
                }
            }
            cache.set(key, numSols)
            return numSols
        }

        fun parseLine(data: String): List<Int> {
            var count = 0
            val result = mutableListOf<Int>()
            data.indices.forEach { i ->
                if (data[i] == '#') {
                    count++
                }
                if (data[i] == '.') {
                    if (count > 0) {
                        result.add(count)
                    }
                    count = 0
                }
            }
            if (count > 0) {
                result.add(count)
            }
            return result
        }

        companion object {
            fun read(line: String, fold: Int): Row {
                val rawRowData = line.split(" ").first()
                val rawArrangementData = line.split(" ").last().split(",").map { it.toInt() }

                var rowData = ""
                val arrangementData = mutableListOf<Int>()
                (0..<fold).forEach {
                    rowData += rawRowData
                    if (it < (fold - 1)) {
                        rowData += "?" // Add a separator
                    }
                    arrangementData.addAll(rawArrangementData)
                }

                return Row(rowData, arrangementData)
            }
        }
    }

}
