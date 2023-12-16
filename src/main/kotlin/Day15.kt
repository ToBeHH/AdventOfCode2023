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
        // we need a linked map to keep the order of the lenses
        val boxes = Array(256) { linkedMapOf<String, Lens>() }
        // parse the input
        lines[0].split(",").forEach { data ->
            // the labels have variable lengths, so we need to find the operation
            val operationIndex = data.indexOfAny(charArrayOf('=', '-'))
            val label = data.substring(0, operationIndex)
            val operation = data[operationIndex]
            val boxNr = hashString(label)
            when (operation) {
                '=' -> {
                    // focal length is only given with the '=' operation
                    val focalLength = data.substring(operationIndex+1).toInt()
                    // as this is a hashmap, it will replace the value if the key already exists
                    boxes[boxNr][label] = Lens(label, focalLength)
                }
                '-' -> {
                    // remove from box
                    boxes[boxNr].remove(label)
                }
            }
        }

        // calculate the sum of all boxes of all focal lengths multiplied by the lens index
        return boxes.mapIndexed { index, box ->
            box.entries.mapIndexed { idx, entry -> entry.value.focalLength * (idx + 1) }.sum() * (index + 1)
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
