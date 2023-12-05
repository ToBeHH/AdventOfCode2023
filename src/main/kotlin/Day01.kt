/**
 * Main method to read the file and give the result
 */
fun main() {
    Day01("day01input.txt").run()
}

class Day01(fileName: String) : BaseDay(fileName) {
    override fun runPart1(): Int {
        return lines.map(this::getInts).sum()
    }

    override fun runPart2(): Int {
        return lines.map(this::getIntsWithText).sum()
    }


    /**
     * Get from the line the first digit and the last digit
     * and form a two-digit number
     *
     * @param line the line to get the digits
     * @return the two-digit number
     */
    internal fun getInts(line: String): Int {
        var firstDigit = 0
        for (i in line.indices) {
            if (line[i].isDigit()) {
                firstDigit = line[i].toString().toInt()
                break
            }
        }
        var lastDigit = 0
        for (i in line.length - 1 downTo 0) {
            if (line[i].isDigit()) {
                lastDigit = line[i].toString().toInt()
                break
            }
        }
        return firstDigit * 10 + lastDigit
    }

    /**
     * Get from the line the first digit and the last digit
     * and form a two-digit number
     *
     * @param line the line to get the digits
     * @return the two-digit number
     */
    internal fun getIntsWithText(line: String): Int {
        var firstDigit = 0
        for (i in line.indices) {
            val d = isDigit(line.substring(i))
            if (d != null) {
                firstDigit = d
                break
            }
        }
        var lastDigit = 0
        for (i in line.length - 1 downTo 0) {
            val d = isDigit(line.substring(i))
            if (d != null) {
                lastDigit = d
                break
            }
        }
        return firstDigit * 10 + lastDigit
    }

    private fun isDigit(str: String): Int? {
        return when {
            str.startsWith("one") -> 1
            str.startsWith("two") -> 2
            str.startsWith("three") -> 3
            str.startsWith("four") -> 4
            str.startsWith("five") -> 5
            str.startsWith("six") -> 6
            str.startsWith("seven") -> 7
            str.startsWith("eight") -> 8
            str.startsWith("nine") -> 9
            str[0].isDigit() -> str[0].code - '0'.code
            else -> null
        }
    }
}