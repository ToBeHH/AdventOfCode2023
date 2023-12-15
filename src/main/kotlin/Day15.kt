/**
 * Main method to read the file and give the result
 */
fun main() {
    Day15("day15input.txt").run()
}

class Day15(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Int {
        return lines[0].split(",").sumOf { hashString(it) }
    }

    override fun runPart2(): Int {
        val boxes = Array(256) { mutableListOf<Lens>()}
        // parse the input
        lines[0].split(",").forEach { data ->
            // the labels have variable lengths, so we need to find the operation
            val operationIndex = data.indexOfAny(charArrayOf('=', '-'))
            val label = data.substring(0, operationIndex)
            val operation = data[operationIndex]
            val boxNr = hashString(label)
            when (operation) {
                '=' -> {
                    val focalLength = data.substring(operationIndex+1).toInt()
                    if (boxes[boxNr].any { it.label == label }) {
                        // replace box as label already exists
                        boxes[boxNr] = boxes[boxNr].map { if (it.label == label) Lens(label, focalLength) else it }.toMutableList()
                    } else {
                        // add to box
                        boxes[boxNr].add(Lens(label, focalLength))
                    }
                }
                '-' -> {
                    // remove from box
                    boxes[boxNr].removeAll { it.label == label }
                }
            }
        }

        return boxes.mapIndexed { index, lst ->
            lst.mapIndexed { ix, l -> l.focalLength * (ix + 1) }.sum() * (index + 1)
        }.sum()
    }

    internal fun hashString(input: String): Int {
        var hashSum = 0
        input.forEach { c ->
            hashSum += c.code
            hashSum = (hashSum * 17) % 256
        }
        return hashSum
    }

    data class Lens(val label: String, val focalLength: Int)
}
