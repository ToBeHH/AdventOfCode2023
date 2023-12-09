/**
 * Main method to read the file and give the result
 */
fun main() {
    Day09("day09input.txt").run()
}

class Day09(fileName: String) : BaseDay(fileName) {
    override fun runPart1(): Int {
        return readReadings().sumOf { it.predictBack() }
    }

    override fun runPart2(): Int {
        return readReadings().sumOf { it.predictFront() }
    }

    internal fun readReadings(): List<Reading> {
        return Reading.read(lines)
    }

    data class Reading(val numbers: List<Int>) {
        companion object {
            fun read(lines: List<String>): List<Reading> {
                return lines.map { line ->
                    Reading(line.split(" ").map { it.toInt() })
                }
            }
        }

        fun predictBack(): Int {
            val differences = calculateDifferences()

            // construct back the predictions
            for (index in differences.lastIndex - 1  downTo 0) {
                differences[index].add(differences[index].last() + differences[index + 1].last())
            }

            // last number is now the prediction
            return differences[0].last()
        }

        fun predictFront(): Int {
            val differences = calculateDifferences()

            // construct back the predictions
            for (index in differences.lastIndex - 1  downTo 0) {
                differences[index].add(0, differences[index].first() - differences[index + 1].first())
            }

            // last number is now the prediction
            return differences[0].first()
        }

        private fun calculateDifferences() : List<MutableList<Int>> {
            val differences = mutableListOf<MutableList<Int>>()

            // add numbers
            differences.add(numbers.toMutableList())

            // build differences until we have reached only 0s
            do {
                val difference = mutableListOf<Int>()
                for (i in 1 until differences.last().size) {
                    difference.add(differences.last()[i] - differences.last()[i - 1])
                }
                differences.add(difference)
            } while ( ! differences.last().all { it == 0 } )

            return differences
        }
    }
}
