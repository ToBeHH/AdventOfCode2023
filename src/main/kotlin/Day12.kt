/**
 * Main method to read the file and give the result
 */
fun main() {
    Day12("day12input.txt").run()
}

class Day12(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Long {
        return readRows(1).sumOf(Row::possibleArrangements)
    }

    override fun runPart2(): Long {
        return readRows(5).sumOf(Row::possibleArrangements)
    }

    internal fun readRows(fold: Int): List<Row> {
        return lines.map { Row.read(it, fold) }
    }

    data class Row(val row: String, val possibleArrangements: List<Int>) {
        fun possibleArrangements(): Long {
            var count: Long = 0

            // The possible arrangements form a binary tree, so we can use a stack to traverse it
            val stack = mutableListOf<String>()
            stack.add(row)
            while (stack.isNotEmpty()) {
                val current = stack.removeAt(0)
                val index = current.indexOf('?')
                if (index >= 0) {
                    stack.add(current.replaceRange(index, index + 1, "."))
                    stack.add(current.replaceRange(index, index + 1, "#"))
                } else {
                    if (parseLine(current) == possibleArrangements) {
                        count++
                    }
                }
            }
            return count
        }

        fun parseLine(data: String) : List<Int> {
            var count = 0
            val result = mutableListOf<Int>()
            for(i in 0..<data.length) {
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
                    arrangementData.addAll(rawArrangementData)
                }

                return Row(rowData, arrangementData)
            }
        }
    }

}
